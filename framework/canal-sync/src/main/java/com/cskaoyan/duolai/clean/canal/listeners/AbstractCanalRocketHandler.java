package com.cskaoyan.duolai.clean.canal.listeners;

import com.cskaoyan.duolai.clean.canal.constants.FieldConstants;
import com.cskaoyan.duolai.clean.canal.constants.OperateType;
import com.cskaoyan.duolai.clean.canal.converter.CanalConverter;
import com.cskaoyan.duolai.clean.canal.model.CanalMqInfo;
import com.cskaoyan.duolai.clean.canal.core.CanalDataHandler;
import com.cskaoyan.duolai.clean.canal.model.dto.CanalBaseDTO;
import com.cskaoyan.duolai.clean.common.utils.CollUtils;
import com.cskaoyan.duolai.clean.common.utils.JsonUtils;
import com.cskaoyan.duolai.clean.common.utils.NumberUtils;
import org.apache.rocketmq.common.message.Message;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractCanalRocketHandler<T> implements CanalDataHandler<T> {

    CanalConverter canalConverter;


    public void init(CanalConverter canalConverter) {
        this.canalConverter = canalConverter;
    }

    // 在ConsumerListener中调用该方法
    public void parseMsg(Message message) throws Exception {

        try {
            // 1.数据格式转换
            String jsonString = new String(message.getBody());
            // canal会将MySQL中变化的数据转化为Json字符串，我们可以将其转化为CanalMqInfo对象
            CanalMqInfo canalMqInfo = JsonUtils.toBean(jsonString, CanalMqInfo.class);
            // 2.过滤数据，没有数据或者非插入、修改、删除的操作均不处理
            if (CollUtils.isEmpty(canalMqInfo.getData()) || !(OperateType.canHandle(canalMqInfo.getType()))) {
                return;
            }

            if (canalMqInfo.getData().size() > 1) {
                // 3.多条数据处理
                batchHandle(canalMqInfo);
            } else {
                // 4.单条数据处理
                singleHandle(canalMqInfo);
            }
        } catch (Exception e) {
            //出现错误延迟1秒重试
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 单条数据处理
     *
     * @param canalMqInfo
     */
    private void singleHandle(CanalMqInfo canalMqInfo) {
        // 1.数据转换
        CanalBaseDTO canalBaseDTO = canalConverter.convertToCanalBaseDTO(canalMqInfo);
        Map<String, Object> fieldMap = CollUtils.getFirst(canalMqInfo.getData());
        canalBaseDTO.setId(parseId(fieldMap));
        canalBaseDTO.setFieldMap(fieldMap);
        canalBaseDTO.setIsSave(canalMqInfo.getIsSave());

        Class<T> messageType = getMessageType();
        if (messageType == null) {
            return;
        }
        switch (canalMqInfo.getType()) {
            case OperateType.INSERT:
                T toInsert = JsonUtils.toBean(JsonUtils.toJsonStr(canalBaseDTO.getFieldMap()), messageType);
                batchSave(Arrays.asList(toInsert));
                break;
            case OperateType.UPDATE:
                T toUpdate = JsonUtils.toBean(JsonUtils.toJsonStr(canalBaseDTO.getFieldMap()), messageType);
                batchUpdate(Arrays.asList(toUpdate));
                break;
            case OperateType.DELETE:
                batchDelete(Arrays.asList(canalBaseDTO.getId()));
                break;
        }
    }


    private void batchHandle(CanalMqInfo canalMqInfo) {
        Class<T> messageType = getMessageType();
        if (messageType == null) {
            return;
        }

        switch (canalMqInfo.getType()) {
            case OperateType.INSERT:
                List<T> toInsert = getTargetList(canalMqInfo, messageType);
                batchSave(toInsert);
                break;
            case OperateType.UPDATE:
                List<T> toUpdate = getTargetList(canalMqInfo, messageType);
                batchUpdate(toUpdate);
                break;
            case OperateType.DELETE:
                List<Long> ids = getIds(canalMqInfo);
                batchDelete(ids);
                break;

        }

    }

    private List<Long> getIds(CanalMqInfo canalMqInfo) {
        List<Long> ids = canalMqInfo.getData().stream()
                .map(fieldMap -> parseId(fieldMap))
                .collect(Collectors.toList());
        return ids;
    }

    private static <T> List<T> getTargetList(CanalMqInfo canalMqInfo, Class<T> messageType) {
        List<T> collect = canalMqInfo.getData().stream()
                .map(fieldMap -> JsonUtils.toBean(JsonUtils.toJsonStr(fieldMap), messageType))
                .collect(Collectors.toList());
        return collect;
    }

    private Long parseId(Map<String, Object> fieldMap) {
        Object objectId = fieldMap.get(FieldConstants.ID);
        return NumberUtils.parseLong(objectId.toString());
    }

    /**
    public abstract void batchDelete(List<Long> ids);
     *
     * @param data
     */
    public abstract void batchSave(List<T> data);

    /**
     * 批量删除
     *
     * @param ids
     */
    public abstract void batchDelete(List<Long> ids);


    public abstract void batchUpdate(List<T> data);


    //获取泛型参数
    public Class<T> getMessageType() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superClass;
            Type[] typeArgs = parameterizedType.getActualTypeArguments();
            if (typeArgs.length > 0 && typeArgs[0] instanceof Class) {
                return (Class<T>) typeArgs[0];
            }
        }
        return null;
    }
}
