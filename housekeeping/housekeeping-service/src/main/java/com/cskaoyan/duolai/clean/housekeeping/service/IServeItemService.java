package com.cskaoyan.duolai.clean.housekeeping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeItemDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeItemSimpleDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeInfoDTO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.ServeItemDO;
import com.cskaoyan.duolai.clean.housekeeping.request.ServeItemCommand;
import com.cskaoyan.duolai.clean.housekeeping.request.ServeItemPageRequest;

import java.util.List;

/**
 * <p>
 * 服务表 服务类
 * </p>
 */
public interface IServeItemService extends IService<ServeItemDO> {
    /**
     * 服务项新增
     *
     * @param serveItemCommand 插入更新服务项
     */
    void addServeItem(ServeItemCommand serveItemCommand);

    /**
     * 服务项修改
     *
     * @param id                    服务项id
     * @param serveItemCommand 插入更新服务项
     * @return 服务项
     */
    ServeItemDTO updateServeItem(Long id, ServeItemCommand serveItemCommand);

    /**
     * 启用服务项
     *
     * @param id 服务项id
     * @return
     */
    ServeItemDTO activateServeItem(Long id);

    /**
     * 禁用服务项
     *
     * @param id 服务项id
     * @return
     */
    void deactivate(Long id);

    /**
     * 服务项删除
     *
     * @param id 服务项id
     */
    void deleteById(Long id);

    /**
     * 根据服务类型id查询关联的启用状态服务项数量
     *
     * @param serveTypeId 服务类型id
     * @return 服务项数量
     */
    Long queryActiveTypeItemCount(Long serveTypeId);

    /**
     * 分页查询
     *
     * @param serveItemPageRequest 查询条件
     * @return 分页结果
     */
    PageDTO<ServeItemDTO> page(ServeItemPageRequest serveItemPageRequest);

    /**
     * 根据id查询详情
     *
     * @param id 服务项id
     * @return 服务项详细信息
     */
    ServeItemDTO queryServeItemAndTypeById(Long id);

    /**
     * 根据id列表批量查询
     *
     * @param ids 服务项id列表
     * @return 服务项简略列表
     */
    List<ServeItemSimpleDTO> queryServeItemListByIds(List<Long> ids);

    /**
     * 查询启用状态的服务项目录
     *
     * @return 服务项目录
     */
    List<ServeTypeInfoDTO> queryActiveServeItemCategory();


    void refreshServeItemCache(Long id);
}
