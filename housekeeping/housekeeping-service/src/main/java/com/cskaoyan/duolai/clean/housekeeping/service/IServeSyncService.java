package com.cskaoyan.duolai.clean.housekeeping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.RegionServeSyncDO;
import com.cskaoyan.duolai.clean.housekeeping.request.RegionServeSyncDTO;

/**
 * <p>
 * 服务同步表 服务类
 * </p>
 */
public interface IServeSyncService extends IService<RegionServeSyncDO> {
    /**
     * 根据服务项id更新
     *
     * @param serveItemId           服务项id
     * @param regionServeSyncDTO 服务同步更新数据
     */
    void updateByServeItemId(Long serveItemId, RegionServeSyncDTO regionServeSyncDTO);

    /**
     * 根据服务类型id更新
     *
     * @param serveTypeId           服务类型id
     * @param regionServeSyncDTO 服务同步更新数据
     */
    void updateByServeTypeId(Long serveTypeId, RegionServeSyncDTO regionServeSyncDTO);
}
