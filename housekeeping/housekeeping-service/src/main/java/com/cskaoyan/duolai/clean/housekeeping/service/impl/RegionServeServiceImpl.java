package com.cskaoyan.duolai.clean.housekeeping.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cskaoyan.duolai.clean.common.expcetions.ForbiddenOperationException;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.housekeeping.converter.RegionServeConverter;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.*;
import com.cskaoyan.duolai.clean.housekeeping.dao.mapper.*;
import com.cskaoyan.duolai.clean.housekeeping.dto.*;
import com.cskaoyan.duolai.clean.housekeeping.enums.HousekeepingStatusEnum;
import com.cskaoyan.duolai.clean.housekeeping.request.RegionServeCommand;
import com.cskaoyan.duolai.clean.housekeeping.request.ServePageRequest;
import com.cskaoyan.duolai.clean.housekeeping.service.IHomeService;
import com.cskaoyan.duolai.clean.housekeeping.service.IRegionServeService;
import com.cskaoyan.duolai.clean.mysql.utils.PageUtils;
import com.cskaoyan.duolai.clean.redis.constants.RedisConstants;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 服务表 服务实现类
 * </p>
 */
@Service
public class RegionServeServiceImpl extends ServiceImpl<RegionServeMapper, RegionServeDO> implements IRegionServeService {

    @Resource
    RegionServeConverter regionServeConverter;

    @Resource
    ServeItemMapper serveItemMapper;

    @Resource
    RegionServeMapper regionServeMapper;

    @Resource
    RegionMapper regionMapper;
    @Resource
    ServeTypeMapper serveTypeMapper;

    @Resource
    ServeSyncMapper serveSyncMapper;


    @Override
    public PageDTO<RegionServeDTO> getPage(ServePageRequest servePageQueryReqDTO) {
        return null;
    }



    @Override
    public void batchAdd(List<RegionServeCommand> regionServeCommandList) {

        for(RegionServeCommand regionServeCommand : regionServeCommandList){
            // 判断服务是否是激活状态，不是抛异常


            // 判断是否重复添加，重复抛异常


            RegionServeDO regionServeDO = regionServeConverter.regionServeCommand2DO(regionServeCommand);

            RegionDO regionDO = regionMapper.selectById(regionServeDO.getRegionId());
            // 设置cityCode
            regionServeDO.setCityCode(regionDO.getCityCode());

            // 保存
        }
    }

    //服务修改, 返回值, regionId和首页缓存有关，在没有实现缓存前先返回null
    @Override
    public RegionServeDetailDTO updatePrice(Long id, Long regionId, BigDecimal price) {


        return null;
    }

    // 服务设置热门/取消，返回值,regionId和首页缓存有关，在没有实现缓存前先返回null
    @Override
    public List<RegionServeDetailDTO> changeHotStatus(Long id, Long regionId,  Integer flag) {

        return null;
    }

    // 需要自己实现
    @Override
    public int queryServeCountByRegionIdAndSaleStatus(Long regionId, Integer saleStatus) {

        return -1;
    }

    // 在ServeItemServiceImpl中被调用，需要自己实现
    @Override
    public int queryServeCountByServeItemIdAndSaleStatus(Long serveItemId, Integer saleStatus) {

        return -1;
    }

    @Override
    public void deleteById(Long id) {

        //草稿状态方可删除
    }

    @Override
    public RegionServeDetailDTO findDetailByIdCache(Long id) {

        return null;
    }

    public RegionServeDetailDTO findDetailByIdDb(Long id) {

        return null;
    }

    @Autowired
    IHomeService iHomeService;

    @Override
    public List<ServeTypeHomeDTO> refreshFirstPageRegionServeList(Long regionId) {
        return null;
    }

    @Override
    public List<RegionServeDetailDTO> refreshFistPageHotServeList(Long regionId) {
        return null;
    }

    @Override
    public List<DisplayServeTypeDTO> refreshFirstPageServeTypeList(Long regionId) {
        return null;
    }

    @Override
    public List<RegionServeDetailDTO> findHotServeListByRegionIdDb(Long regionId) {
        //校验当前城市是否为启用状态, 否则抛异常


        return null;
    }



    // 上架，返回值和首页缓存有关，在没有实现缓存前先返回null
    @Override
    @Transactional
    public RegionServeDetailDTO onSale(Long id) {
        RegionServeDO serve = baseMapper.selectById(id);


        //草稿或下架状态方可上架

        //服务项为启用状态方可上架


        // 区域为启用状态方可上架

        //更新上架状态


        // 添加到修改同步表中
        return null;
    }


    // 下架， 返回值和首页缓存有关，在没有实现缓存前先返回null
    @Override
    @Transactional
    public RegionServeDTO offSale(Long id) {

        // 注意上架状态方可下架



        return null;
    }

    @Override
    public ServeDetailDTO findServeDetailById(Long id) {
        ServeDetailDO serveDetailById = regionServeMapper.findServeDetailById(id);
        return regionServeConverter.serveDetailDO2DTO(serveDetailById);
    }
}
