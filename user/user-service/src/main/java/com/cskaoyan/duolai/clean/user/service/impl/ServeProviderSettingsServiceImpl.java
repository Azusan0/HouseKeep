package com.cskaoyan.duolai.clean.user.service.impl;

import com.cskaoyan.duolai.clean.common.utils.CollUtils;
import com.cskaoyan.duolai.clean.common.utils.NumberUtils;
import com.cskaoyan.duolai.clean.common.utils.ObjectUtils;
import com.cskaoyan.duolai.clean.user.converter.ServeProviderSettingConverter;
import com.cskaoyan.duolai.clean.user.dao.mapper.ServeProviderSettingsMapper;
import com.cskaoyan.duolai.clean.user.dao.entity.ServeProviderDO;
import com.cskaoyan.duolai.clean.user.dao.entity.ServeProviderSettingsDO;
import com.cskaoyan.duolai.clean.user.dao.entity.ServeProviderSyncDO;
import com.cskaoyan.duolai.clean.user.request.ServePickUpReqDTO;
import com.cskaoyan.duolai.clean.user.request.ServeScopeCommand;
import com.cskaoyan.duolai.clean.user.dto.CertificationStatusDTO;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderDTO;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderSettingsDTO;
import com.cskaoyan.duolai.clean.user.dto.ServeSettingsStatusDTO;
import com.cskaoyan.duolai.clean.user.service.IServeProviderService;
import com.cskaoyan.duolai.clean.user.service.IServeProviderSettingsService;
import com.cskaoyan.duolai.clean.user.service.IServeProviderSyncService;
import com.cskaoyan.duolai.clean.common.enums.EnableStatusEnum;
import com.cskaoyan.duolai.clean.common.expcetions.DBException;
import com.cskaoyan.duolai.clean.mvc.utils.UserContext;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务人员附属信息 服务实现类
 * </p>
 */
@Service
public class ServeProviderSettingsServiceImpl extends ServiceImpl<ServeProviderSettingsMapper, ServeProviderSettingsDO> implements IServeProviderSettingsService {

    @Resource
    private IServeProviderService serveProviderService;

    @Resource
    private IServeProviderSyncService serveProviderSyncService;


    @Resource
    ServeProviderSettingConverter serveProviderSettingConverter;



    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void add(Long id) {
        ServeProviderSettingsDO serveProviderSettingsDO = new ServeProviderSettingsDO();
        serveProviderSettingsDO.setId(id);
        if(baseMapper.insert(serveProviderSettingsDO) <= 0){
            throw new DBException("请求失败");
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void setServeScope(ServeScopeCommand serveScopeCommand) {
        String[] latAndLon = serveScopeCommand.getLocation().split(",");
        // 经度
        double lon = NumberUtils.parseDouble(latAndLon[0]);
        // 纬度
        double lat = NumberUtils.parseDouble(latAndLon[1]);
        // 更新服务范围
        ServeProviderSettingsDO serveProviderSettingsDO =  serveProviderSettingConverter
                .serveScopeCommandToServeProviderSettingsDO(serveScopeCommand);
        serveProviderSettingsDO.setId(UserContext.currentUserId());
        serveProviderSettingsDO.setLat(lat);
        serveProviderSettingsDO.setLon(lon);
        baseMapper.updateById(serveProviderSettingsDO);

    }

    @Override
    public ServeProviderSettingsDTO getServeScope() {
        Long currentUserId = UserContext.currentUserId();
        ServeProviderSettingsDO serveProviderSettingsDO = baseMapper.selectById(currentUserId);
        if(serveProviderSettingsDO == null) {
            return new ServeProviderSettingsDTO();
        }
        ServeProviderSettingsDTO serveScopeResDTO = serveProviderSettingConverter
                .serveProviderSettingsDOToDTO(serveProviderSettingsDO);

        if(ObjectUtils.isNotNull(serveProviderSettingsDO.getLon())) {
            serveScopeResDTO.setLocation(serveProviderSettingsDO.getLon() + "," + serveProviderSettingsDO.getLat());
        }
        return serveScopeResDTO;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void setPickUp(Long id, Integer canPickUp) {
        // 1.更新接单信息
        ServeProviderSettingsDO serveProviderSettingsDO = new ServeProviderSettingsDO();
        serveProviderSettingsDO.setId(id);
        serveProviderSettingsDO.setCanPickUp(canPickUp);
        baseMapper.updateById(serveProviderSettingsDO);
    }

    //返回的设置状态在前端需要使用
    @Override
    public ServeSettingsStatusDTO getSettingStatus() {

        //当前用户id(当前的服务从业者id)
        long currentUserId = UserContext.currentUserId();

        /*
            查询服务人员设置信息, 并获取以下状态:
            1. 判断serve_provider_settings的can_pick_up,是否开启接单 canPickUp
            2. 判断serve_provider_settings是否设置服务范围（看经纬度，以及接单位置(intentionScope)是否设置） serveScopeSetted
            3. 判断serve_provider_settings是否设置技能(判断haveSkill） serveSkillSetted
            4. 判断serve_provider表的的setting_status是否设置为1 settingsStatus
         */

        /*
              获取认证状态certificationStatus(根据从业者id查询表work_certification获取最新的认证记录的状态), 如果没查询到记录，认证状态默认为0。
              可以调用该方法获取认证状态: serveProviderService.getCertificationStatus(currentUserId)
         */

        //认证通过，设置服务技能，设置服务范围则更新首次设置状态为完成(settingsStatus == 0说明之前未设置完成状态)
        //if(settingsStatus == 0 && serveSkillSetted && serveScopeSetted && certificationStatus==2){
        //    serveProviderService.settingStatus(currentUserId);


        //}


        // 构造响应
 //       ServeSettingsStatusDTO serveSettingsStatusDTO = ServeSettingsStatusDTO.builder()
//                        .certificationStatus(certificationStatus)//认证状态
//                        .settingsStatus(settingsStatus)//首先设置状态是否完成
//                        .serveSkillSetted(serveSkillSetted)//是否设置服务技能
//                        .serveScopeSetted(serveScopeSetted)//是否设置服务范围
//                        .canPickUp(canPickUp)//开启接单状态
//                        .build();

//        return serveSettingsStatusDTO;
        return null;
    }

    @Override
    public ServeProviderSettingsDO findById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void setHaveSkill(Long currentUserId) {
        lambdaUpdate()
                .eq(ServeProviderSettingsDO::getId, currentUserId)
                .set(ServeProviderSettingsDO::getHaveSkill, 1)
                .update();
    }

    @Override
    public Map<Long, String> findManyCityCodeOfServeProvider(List<Long> ids) {
        if(CollUtils.isEmpty(ids)) {
            return new HashMap<>();
        }
        List<ServeProviderSettingsDO> serveProviderSettingDOS = baseMapper.batchQueryCityCodeByIds(ids);
        return CollUtils.isEmpty(serveProviderSettingDOS) ? new HashMap<>() :
                serveProviderSettingDOS.stream().collect(Collectors.toMap(ServeProviderSettingsDO::getId, ServeProviderSettingsDO::getCityCode));

    }


}
