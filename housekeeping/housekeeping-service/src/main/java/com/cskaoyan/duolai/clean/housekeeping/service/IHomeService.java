package com.cskaoyan.duolai.clean.housekeeping.service;


import com.cskaoyan.duolai.clean.housekeeping.dto.DisplayServeTypeDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.RegionServeDetailDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeHomeDTO;

import java.util.List;

/**
 * 首页查询相关功能
 **/
public interface IHomeService {


    /**
     * 根据区域id从缓存中获取服务图标信息
     *
     * @param regionId 区域id
     * @return 服务图标列表
     */
    List<ServeTypeHomeDTO> queryServeIconCategoryByRegionIdCache(Long regionId);

    /**
     * 根据区域id从数据库中获取服务图标信息
     *
     * @param regionId 区域id
     * @return 服务图标列表
     */
    List<ServeTypeHomeDTO> queryServeIconCategoryByRegionIdDb(Long regionId);

    /**
     * 根据区域id从缓存中查询热门服务列表
     *
     * @param regionId 区域id
     * @return 服务列表
     */
    List<RegionServeDetailDTO> findHotServeListByRegionIdCache(Long regionId);

    /**
     * 根据区域id从缓存中查询已开通的服务类型
     *
     * @param regionId 区域id
     * @return 已开通的服务类型
     */
    List<DisplayServeTypeDTO> queryServeTypeListByRegionIdCache(Long regionId);


    /**
     * 根据区域id从数据库中查询已开通的服务类型
     *
     * @param regionId 区域id
     * @return 已开通的服务类型
     */
    List<DisplayServeTypeDTO> queryServeTypeListByRegionIdDb(Long regionId);

}
