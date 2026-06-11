package com.cskaoyan.duolai.clean.housekeeping.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cskaoyan.duolai.clean.housekeeping.converter.ConfigRegionConverter;
import com.cskaoyan.duolai.clean.housekeeping.dto.ConfigRegionDTO;
import com.cskaoyan.duolai.clean.housekeeping.dao.mapper.ConfigRegionMapper;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.ConfigRegionDO;
import com.cskaoyan.duolai.clean.housekeeping.request.ConfigRegionCommand;
import com.cskaoyan.duolai.clean.housekeeping.service.IConfigRegionService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 区域业务配置 服务实现类
 * </p>
 */
@Service
public class ConfigRegionServiceImpl extends ServiceImpl<ConfigRegionMapper, ConfigRegionDO> implements IConfigRegionService {

    @Resource
    ConfigRegionConverter configRegionConverter;

    @Override
    public ConfigRegionDTO queryById(Long id) {
        ConfigRegionDO configRegion = baseMapper.selectById(id);
        return configRegionConverter.configRegionDOToDTO(configRegion);
    }

    public void setConfigRegionById(Long id, ConfigRegionCommand configRegionCommand) {
        ConfigRegionDO configRegion = configRegionConverter.configRegionCommandToDO(configRegionCommand);
        configRegion.setId(id);
        baseMapper.updateById(configRegion);
    }

    @Override
    public void init(Long id, String cityCode) {
        ConfigRegionDO configRegion = ConfigRegionDO.builder()
                .id(id)
                .cityCode(cityCode)
                // 个人接单数量限制，默认10个
                .staffReceiveOrderMax(10)
                // 个人接单范围半径 50公里
                .staffServeRadius(50)
                // 分流时间间隔120分钟，即下单时间与服务预计开始时间的间隔
                .diversionInterval(120)
                // 抢单超时时间，默认60分钟
                .seizeTimeoutInterval(60)
                // 派单策略默认距离优先策略
                .dispatchStrategy(1)
                // 派单每轮时间间隔，默认180s
                .dispatchPerRoundInterval(180)
                .build();
        baseMapper.insert(configRegion);
    }

    @Override
    public List<ConfigRegionDTO> queryAll() {
        List<ConfigRegionDO> list = lambdaQuery().list();
        return configRegionConverter.configRegionDOsToDTOs(list);
    }

    @Override
    public ConfigRegionDTO queryByCityCode(String cityCode) {
        ConfigRegionDO configRegion = lambdaQuery()
                .eq(ConfigRegionDO::getCityCode, cityCode)
                .one();
        return configRegionConverter.configRegionDOToDTO(configRegion);
    }
}
