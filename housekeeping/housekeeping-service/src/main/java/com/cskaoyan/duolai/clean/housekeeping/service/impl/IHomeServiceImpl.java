package com.cskaoyan.duolai.clean.housekeeping.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.cskaoyan.duolai.clean.housekeeping.converter.RegionServeConverter;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.RegionDO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.ServeTypeDO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.ServeTypeHomeDO;
import com.cskaoyan.duolai.clean.housekeeping.dao.mapper.RegionServeMapper;
import com.cskaoyan.duolai.clean.housekeeping.dto.DisplayServeTypeDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.RegionServeDetailDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.RegionServeHomeDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeHomeDTO;
import com.cskaoyan.duolai.clean.housekeeping.enums.HousekeepingStatusEnum;
import com.cskaoyan.duolai.clean.housekeeping.service.IHomeService;
import com.cskaoyan.duolai.clean.housekeeping.service.IRegionServeService;
import com.cskaoyan.duolai.clean.housekeeping.service.IRegionService;
import com.cskaoyan.duolai.clean.redis.constants.RedisConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 首页查询相关功能
 **/
@Slf4j
@Service
public class IHomeServiceImpl implements IHomeService {
    @Resource
    private IRegionService regionService;
    @Resource
    private IRegionServeService serveService;
    @Resource
    private RegionServeMapper serveMapper;
    @Resource
    RegionServeConverter regionServeConverter;



    /**
     * 根据区域id获取服务图标信息
     *
     * @param regionId 区域id
     * @return 服务图标列表
     */
    @Override
    public List<ServeTypeHomeDTO> queryServeIconCategoryByRegionIdCache(Long regionId) {

        return null;
    }

    public List<ServeTypeHomeDTO> queryServeIconCategoryByRegionIdDb(Long regionId) {

        return null;
    }


    /**
     * 根据区域id查询热门服务列表
     *
     * @param regionId 区域id
     * @return 服务列表
     */
    @Override
    public List<RegionServeDetailDTO> findHotServeListByRegionIdCache(Long regionId) {

        return null;
    }


    /**
     * 根据区域id查询已开通的服务类型
     *
     * @param regionId 区域id
     * @return 已开通的服务类型
     */
    @Override
    public List<DisplayServeTypeDTO> queryServeTypeListByRegionIdCache(Long regionId) {
        return null;
    }

    public List<DisplayServeTypeDTO> queryServeTypeListByRegionIdDb(Long regionId) {

        return null;
    }






}
