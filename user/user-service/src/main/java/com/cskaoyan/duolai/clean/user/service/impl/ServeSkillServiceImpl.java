package com.cskaoyan.duolai.clean.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeItemSimpleDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeInfoDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeSimpleDTO;
import com.cskaoyan.duolai.clean.user.client.ServeItemApi;
import com.cskaoyan.duolai.clean.user.client.ServeTypeApi;
import com.cskaoyan.duolai.clean.user.converter.ServeSkillConverter;
import com.cskaoyan.duolai.clean.user.dao.mapper.ServeSkillMapper;
import com.cskaoyan.duolai.clean.user.dao.entity.ServeProviderSyncDO;
import com.cskaoyan.duolai.clean.user.dao.entity.ServeSkillDO;
import com.cskaoyan.duolai.clean.user.request.ServeSkillCommand;
import com.cskaoyan.duolai.clean.user.dto.ServeSkillInfoDTO;
import com.cskaoyan.duolai.clean.user.service.IServeProviderService;
import com.cskaoyan.duolai.clean.user.service.IServeProviderSettingsService;
import com.cskaoyan.duolai.clean.user.service.IServeProviderSyncService;
import com.cskaoyan.duolai.clean.user.service.IServeSkillService;
import com.cskaoyan.duolai.clean.common.model.CurrentUserInfo;
import com.cskaoyan.duolai.clean.common.utils.CollUtils;
import com.cskaoyan.duolai.clean.mvc.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务技能表 服务实现类
 * </p>
 */
@Service
public class ServeSkillServiceImpl extends ServiceImpl<ServeSkillMapper, ServeSkillDO> implements IServeSkillService {
    @Resource
    private ServeTypeApi serveTypeApi;
    @Resource
    private ServeItemApi serveItemApi;
    @Resource
    private IServeProviderService serveProviderService;
    @Resource
    private IServeProviderSettingsService serveProviderSettingsService;

    @Resource
    private ServeSkillConverter serveSkillConverter;

    @Autowired
    IServeProviderSyncService serveProviderSyncService;

    /**
     * 批量新增或修改
     *
     * @param serveSkillCommandList 批量新增或修改数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpsert(List<ServeSkillCommand> serveSkillCommandList) {
        CurrentUserInfo currentUserInfo = UserContext.currentUser();

        //1.删除上一次该用户设置的服务技能(serve_skill)


        //2.添加新的服务技能(serve_skill), 不要忘了给每个技能设置其的serveProviderId


        // 3.设置技能，调用serveProviderSettingsService.setHaveSkill
    }

    /**
     * 查询服务技能目录
     *
     * @return 服务技能目录
     */
    @Override
    public List<ServeSkillInfoDTO> category() {
        //1.查询当前用户的服务技能(serve_skill)
        List<ServeSkillDO> serveSkillDOList = null;

        //2.调用家政服务查询启用状态的服务项目录：serveItemApi.queryActiveServeItemCategoryInfo()
        List<ServeTypeInfoDTO> serveTypeInfoDTOs = null;

        //3.封装数据，计算服务类型下属服务技能数量、判断技能是否选中
        // 3.1 将从家政服务获取的数据serveTypeInfoDTOs转化为ServeSkillInfoDTO的List
        List<ServeSkillInfoDTO> result = null;
        // 获取Map<服务类型, 服务项数量> (serveSkillDOList表示服务提供者已经设置过的技能列表)
        Map<Long, Long> typeCountMap = serveSkillDOList.stream()
                .collect(Collectors.groupingBy(ServeSkillDO::getServeTypeId, Collectors.counting()));
        // 获取已有服务项id集合
        List<Long> serveItemIds = serveSkillDOList.stream()
                .map(ServeSkillDO::getServeItemId).collect(Collectors.toList());

        result.forEach(type -> {
            Long count = typeCountMap.get(type.getServeTypeId());
            type.setCount(null == count ? 0 : count.intValue());
            // 设置每个服务类型下的每个服务项的是否已被设置
            type.getServeSkillItemDTOList()
                    .forEach(item -> item.setIsSelected(serveItemIds.contains(item.getServeItemId())));
        });
        return result;
    }

    /**
     * 查询服务者的服务技能
     *
     * @param providerId   服务者id
     * @param providerType 服务者类型
     * @param cityCode     城市编码
     * @return 服务技能列表
     */
    @Override
    public List<Long> queryServeSkillListByServeProvider(Long providerId, Integer providerType, String cityCode) {
        //1.获取服务者的所有服务技能
        LambdaQueryWrapper<ServeSkillDO> queryWrapper = Wrappers.<ServeSkillDO>lambdaQuery()
                .eq(ServeSkillDO::getServeProviderId, providerId);
        List<ServeSkillDO> serveSkillDOList = baseMapper.selectList(queryWrapper);
        if (ObjectUtil.isEmpty(serveSkillDOList)) {
            return Collections.emptyList();
        }

        //2.从技能中提取服务项列表
        List<Long> skillServeItemIds =
                serveSkillDOList.stream().map(ServeSkillDO::getServeItemId).collect(Collectors.toList());

        return skillServeItemIds;
    }


}
