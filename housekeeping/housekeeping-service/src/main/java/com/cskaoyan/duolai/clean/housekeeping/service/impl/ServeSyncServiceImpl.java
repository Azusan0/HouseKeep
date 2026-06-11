package com.cskaoyan.duolai.clean.housekeeping.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cskaoyan.duolai.clean.housekeeping.dao.mapper.ServeSyncMapper;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.RegionServeSyncDO;
import com.cskaoyan.duolai.clean.housekeeping.request.RegionServeSyncDTO;
import com.cskaoyan.duolai.clean.housekeeping.service.IServeSyncService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务同步表 服务实现类
 * </p>
 */
@Service
public class ServeSyncServiceImpl extends ServiceImpl<ServeSyncMapper, RegionServeSyncDO> implements IServeSyncService {
    /**
     * 根据服务项id更新
     *
     * @param serveItemId           服务项id
     * @param regionServeSyncDTO 服务同步更新数据
     */
    @Override
    public void updateByServeItemId(Long serveItemId, RegionServeSyncDTO regionServeSyncDTO) {
        LambdaUpdateWrapper<RegionServeSyncDO> updateWrapper = Wrappers.<RegionServeSyncDO>lambdaUpdate()
                .eq(RegionServeSyncDO::getServeItemId, serveItemId)
                .set(RegionServeSyncDO::getServeItemName, regionServeSyncDTO.getServeItemName())
                .set(RegionServeSyncDO::getServeItemSortNum, regionServeSyncDTO.getServeItemSortNum())
                .set(RegionServeSyncDO::getUnit, regionServeSyncDTO.getUnit())
                .set(RegionServeSyncDO::getServeItemImg, regionServeSyncDTO.getServeItemImg())
                .set(RegionServeSyncDO::getServeItemIcon, regionServeSyncDTO.getServeItemIcon());
        super.update(updateWrapper);
    }

    /**
     * 根据服务类型id更新
     *
     * @param serveTypeId           服务类型id
     * @param regionServeSyncDTO 服务同步更新数据
     */
    @Override
    public void updateByServeTypeId(Long serveTypeId, RegionServeSyncDTO regionServeSyncDTO) {
        LambdaUpdateWrapper<RegionServeSyncDO> updateWrapper = Wrappers.<RegionServeSyncDO>lambdaUpdate()
                .eq(RegionServeSyncDO::getServeTypeId, serveTypeId)
                .set(RegionServeSyncDO::getServeTypeName, regionServeSyncDTO.getServeTypeName())
                .set(RegionServeSyncDO::getServeTypeImg, regionServeSyncDTO.getServeTypeImg())
                .set(RegionServeSyncDO::getServeTypeIcon, regionServeSyncDTO.getServeTypeIcon())
                .set(RegionServeSyncDO::getServeTypeSortNum, regionServeSyncDTO.getServeTypeSortNum());
        super.update(updateWrapper);
    }
}
