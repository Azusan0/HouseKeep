package com.cskaoyan.duolai.clean.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cskaoyan.duolai.clean.user.dao.entity.ServeProviderSettingsDO;
import com.cskaoyan.duolai.clean.user.request.ServePickUpReqDTO;
import com.cskaoyan.duolai.clean.user.request.ServeScopeCommand;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderSettingsDTO;
import com.cskaoyan.duolai.clean.user.dto.ServeSettingsStatusDTO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务人员附属信息 服务类
 * </p>
 */
public interface IServeProviderSettingsService extends IService<ServeProviderSettingsDO> {

    void add(Long id);

    /**
     * 设置服务范围
     * @param serveScopeCommand
     */
    void setServeScope(ServeScopeCommand serveScopeCommand);

    /**
     * 获取服务范围
     * @return
     */
    ServeProviderSettingsDTO getServeScope();

    /**
     * 设置接单状态
     * @param id 服务人员id
     * @param canPickUp 是否开启接单
     */
    void setPickUp(Long id, Integer canPickUp);

    /**
     * 获取设置状态
     * @return
     */
    ServeSettingsStatusDTO getSettingStatus();

    ServeProviderSettingsDO findById(Long id);


    /**
     * 标记已设置服务技能
     * @param currentUserId
     */
    void setHaveSkill(Long currentUserId);


    /**
     * 批量获取服务人员或机构所在城市编码
     * @param serveProviderIds
     * @return
     */
    Map<Long, String> findManyCityCodeOfServeProvider(List<Long> serveProviderIds);

}
