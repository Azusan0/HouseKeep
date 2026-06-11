package com.cskaoyan.duolai.clean.housekeeping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cskaoyan.duolai.clean.housekeeping.dto.ConfigRegionDTO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.ConfigRegionDO;
import com.cskaoyan.duolai.clean.housekeeping.request.ConfigRegionCommand;

import java.util.List;

/**
 * <p>
 * 区域业务配置 服务类
 * </p>
 */
public interface IConfigRegionService extends IService<ConfigRegionDO> {

    /**
     * 获取区域配置
     *
     * @param id 区域id
     * @return 区域配置信息
     */
    ConfigRegionDTO queryById(Long id);

    /**
     * 设置区域业务配置
     *
     * @param id 区域id
     * @param configRegionCommand 区域配置
     */
    void setConfigRegionById(Long id, ConfigRegionCommand configRegionCommand);

    /**
     * 初始化区域配置
     * @param id
     * @param cityCode
     */
    void init(Long id, String cityCode);

    /**
     * 查询所有的区域配置
     *
     * @return
     */
    List<ConfigRegionDTO> queryAll();

    /**
     * 根据城市编码获取区域配置
     *
     * @param cityCode 城市编码
     * @return 区域配置
     */
    ConfigRegionDTO queryByCityCode(String cityCode);
}
