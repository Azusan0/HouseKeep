package com.cskaoyan.duolai.clean.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cskaoyan.duolai.clean.user.dao.mapper.ServeProviderSyncMapper;
import com.cskaoyan.duolai.clean.user.dao.entity.ServeProviderSyncDO;
import com.cskaoyan.duolai.clean.user.service.IServeProviderSyncService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 评分同步列表 服务实现类
 * </p>
 */
@Service
public class ServeProviderSyncServiceImpl extends ServiceImpl<ServeProviderSyncMapper, ServeProviderSyncDO> implements IServeProviderSyncService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(Long id) {
        ServeProviderSyncDO serveProviderSyncDO = new ServeProviderSyncDO();
        serveProviderSyncDO.setId(id);
        serveProviderSyncDO.setStatus(0);//默认0
        return baseMapper.insert(serveProviderSyncDO);
    }


}
