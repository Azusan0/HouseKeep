package com.cskaoyan.duolai.clean.housekeeping.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 */
public interface RegionServeMapper extends BaseMapper<RegionServeDO> {

    /**
     * 区域服务查询
     * @param regionId 区域id
     * @return
     */
    Page<RegionServeDO> queryRegionServeListByRegionId(@Param("regionId") Long regionId, @Param("pageParam")Page page);

     List<ServeTypeHomeDO>  findServeIconCategoryByRegionId( Long regionId);

     List<RegionServeDetailDO> findHotServeListByRegionId(Long regionId);

     /*
         查询的是regionServe，ServeType，以及ServeItem信息
     */
    ServeDetailDO findServeDetailById( Long id);

    /*
        查询的是regionServe，ServeType，以及ServeItem信息
    */
    RegionServeDetailDO findRegionServeDetail(Long id);

    List<ServeTypeDO> findServeTypeListByRegionId(Long regionId);

    List<RegionServeDetailDO> findRegionServeDetailByRegionId(Long regionId);
}
