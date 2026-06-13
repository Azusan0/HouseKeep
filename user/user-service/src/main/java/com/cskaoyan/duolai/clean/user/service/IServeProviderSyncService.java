package com.cskaoyan.duolai.clean.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cskaoyan.duolai.clean.user.dao.entity.ServeProviderSyncDO;

/**
 * <p>
 * 评分同步列表 服务类
 * </p>
 */
public interface IServeProviderSyncService extends IService<ServeProviderSyncDO> {

    int add(Long id);

}
