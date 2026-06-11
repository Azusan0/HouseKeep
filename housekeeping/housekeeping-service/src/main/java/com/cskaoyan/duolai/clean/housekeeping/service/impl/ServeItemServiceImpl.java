package com.cskaoyan.duolai.clean.housekeeping.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cskaoyan.duolai.clean.common.expcetions.ForbiddenOperationException;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.housekeeping.converter.ServeItemConverter;
import com.cskaoyan.duolai.clean.housekeeping.converter.ServeTypeConverter;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeItemDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeItemSimpleDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeInfoDTO;
import com.cskaoyan.duolai.clean.housekeeping.enums.HousekeepingStatusEnum;
import com.cskaoyan.duolai.clean.housekeeping.dao.mapper.RegionServeMapper;
import com.cskaoyan.duolai.clean.housekeeping.dao.mapper.ServeItemMapper;
import com.cskaoyan.duolai.clean.housekeeping.dao.mapper.ServeTypeMapper;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.RegionServeDO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.ServeItemDO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.ServeTypeDO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.ServeTypeInfoDO;
import com.cskaoyan.duolai.clean.housekeeping.request.RegionServeSyncDTO;
import com.cskaoyan.duolai.clean.housekeeping.request.ServeItemCommand;
import com.cskaoyan.duolai.clean.housekeeping.request.ServeItemPageRequest;
import com.cskaoyan.duolai.clean.housekeeping.service.IRegionServeService;
import com.cskaoyan.duolai.clean.housekeeping.service.IServeItemService;
import com.cskaoyan.duolai.clean.housekeeping.service.IServeSyncService;
import com.cskaoyan.duolai.clean.mysql.utils.PageUtils;
import com.cskaoyan.duolai.clean.redis.constants.RedisConstants;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务表 服务实现类
 * </p>
 */
@Service
public class ServeItemServiceImpl extends ServiceImpl<ServeItemMapper, ServeItemDO> implements IServeItemService {
    @Resource
    private IServeSyncService serveSyncService;

    @Resource
    private ServeTypeMapper serveTypeMapper;

    @Resource
    ServeItemConverter serveItemConverter;

    @Resource
    RegionServeMapper regionServeMapper;

    @Resource
    IRegionServeService regionServeService;


    @Resource
    ServeTypeConverter serveTypeConverter;

    @Resource
    RedissonClient redissonClient;

    /**
     * 服务项新增
     *
     * @param serveItemCommand 新增服务项
     */
    @Override
    public void addServeItem(ServeItemCommand serveItemCommand) {
        //校验名称是否重复
        LambdaQueryWrapper<ServeItemDO> queryWrapper = Wrappers.<ServeItemDO>lambdaQuery().eq(ServeItemDO::getName, serveItemCommand.getName());
        Long count = baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new ForbiddenOperationException("服务项名称不可重复");
        }

        ServeItemDO serveItemDO = serveItemConverter.serveItemCommandToServItemDO(serveItemCommand);
        serveItemDO.setCode(IdUtil.getSnowflakeNextIdStr());
        baseMapper.insert(serveItemDO);
    }

    /**
     * 服务项修改
     *
     * @param id                    服务项id
     * @param serveItemCommand 插入更新服务项
     * @return 服务项
     */
    @Override
    @Transactional
    public ServeItemDTO updateServeItem(Long id, ServeItemCommand serveItemCommand) {

        ServeItemDO oldServeItem = baseMapper.selectById(id);
        //草稿或禁用状态方可启用
        if ((HousekeepingStatusEnum.INIT.getStatus() != oldServeItem.getActiveStatus())
                && (HousekeepingStatusEnum.DISABLE.getStatus() != oldServeItem.getActiveStatus())) {
            throw new ForbiddenOperationException("草稿或禁用状态方可启用");
        }

        //1.更新服务项
        ServeItemDO serveItemDO = serveItemConverter.serveItemCommandToServItemDO(serveItemCommand);
        serveItemDO.setId(id);
        baseMapper.updateById(serveItemDO);


        //2.同步数据到es
        RegionServeSyncDTO regionServeSyncDTO = serveItemConverter.serveItemCommandToRegionServeSyncDTO(serveItemCommand);;
        regionServeSyncDTO.setServeItemName(serveItemCommand.getName());
        regionServeSyncDTO.setServeItemImg(serveItemCommand.getImg());
        regionServeSyncDTO.setServeItemIcon(serveItemCommand.getServeItemIcon());
        regionServeSyncDTO.setServeItemSortNum(serveItemCommand.getSortNum());
        serveSyncService.updateByServeItemId(id, regionServeSyncDTO);

        //用于更新缓存
        ServeItemDO resultDO = baseMapper.selectById(id);
        return serveItemConverter.serveItemDOToServeItemDTO(resultDO);
    }


    /**
     * 启用服务项
     *
     * @param id 服务项id
     * @return
     */
    @Override
    @Transactional
    public ServeItemDTO activateServeItem(Long id) {

        //查询服务项
        ServeItemDO serveItemDO = baseMapper.selectById(id);
        if (ObjectUtil.isNull(serveItemDO)) {
            throw new ForbiddenOperationException("服务项不存在");
        }
        //启用状态
        Integer activeStatus = serveItemDO.getActiveStatus();
        //草稿或禁用状态方可启用
        if (!(HousekeepingStatusEnum.INIT.getStatus() == activeStatus || HousekeepingStatusEnum.DISABLE.getStatus() == activeStatus)) {
            throw new ForbiddenOperationException("草稿或禁用状态方可启用");
        }
        //服务类型id
        Long serveTypeId = serveItemDO.getServeTypeId();
        //服务类型信息
        ServeTypeDO serveTypeDO = serveTypeMapper.selectById(serveTypeId);
        if (ObjectUtil.isNull(serveTypeDO)) {
            throw new ForbiddenOperationException("所属服务类型不存在");
        }
        //所属服务类型为启用状态时方可启用
        if (!(HousekeepingStatusEnum.ENABLE.getStatus() == serveTypeDO.getActiveStatus())) {
            throw new ForbiddenOperationException("所属服务类型为启用状态时方可启用");
        }

        //更新启用状态
        LambdaUpdateWrapper<ServeItemDO> updateWrapper = Wrappers.<ServeItemDO>lambdaUpdate().eq(ServeItemDO::getId, id).set(ServeItemDO::getActiveStatus, HousekeepingStatusEnum.ENABLE.getStatus());
        update(updateWrapper);

        ServeItemDO resultDO = baseMapper.selectById(id);
        return serveItemConverter.serveItemDOToServeItemDTO(resultDO);
    }

    /**
     * 禁用服务项
     *
     * @param id 服务项id
     * @return
     */
    @Override
    @Transactional
    public void deactivate(Long id) {
        //查询服务项
        ServeItemDO serveItemDO = baseMapper.selectById(id);
        if (ObjectUtil.isNull(serveItemDO)) {
            throw new ForbiddenOperationException("服务项不存在");
        }
        //启用状态
        Integer activeStatus = serveItemDO.getActiveStatus();
        //启用状态方可禁用
        if (!(HousekeepingStatusEnum.ENABLE.getStatus() == activeStatus)) {
            throw new ForbiddenOperationException("启用状态方可禁用");
        }

        //有区域在使用该服务将无法禁用（存在关联的区域服务且状态为上架表示有区域在使用该服务项）
        int onSaleCount = regionServeService
                // 这个方法需要你们实现
                .queryServeCountByServeItemIdAndSaleStatus(id, HousekeepingStatusEnum.ENABLE.getStatus());
        if (onSaleCount > 0) {
            throw new ForbiddenOperationException("尚有区域正在使用服务" + serveItemDO.getName());
        }

        //更新禁用状态
        LambdaUpdateWrapper<ServeItemDO> updateWrapper = Wrappers.<ServeItemDO>lambdaUpdate().eq(ServeItemDO::getId, id).set(ServeItemDO::getActiveStatus, HousekeepingStatusEnum.DISABLE.getStatus());
        update(updateWrapper);
    }


    /**
     * 服务项删除
     *
     * @param id 服务项id
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        ServeItemDO serveItemDO = baseMapper.selectById(id);
        if (ObjectUtil.isNull(serveItemDO)) {
            throw new ForbiddenOperationException("服务项不存在");
        }
        //启用状态
        Integer activeStatus = serveItemDO.getActiveStatus();

        //1.删除校验：只有草稿状态方可删除
        if (!(HousekeepingStatusEnum.INIT.getStatus() == activeStatus)) {
            throw new ForbiddenOperationException("只有草稿状态方可删除");
        }

        //2.根据id删除
        baseMapper.deleteById(id);
    }

    /**
     * 根据服务类型id查询关联的启用状态服务项数量
     *
     * @param serveTypeId 服务类型id
     * @return 服务项数量
     */
    @Override
    public Long queryActiveTypeItemCount(Long serveTypeId) {
        LambdaQueryWrapper<ServeItemDO> queryWrapper = Wrappers.<ServeItemDO>lambdaQuery()
                .eq(ServeItemDO::getServeTypeId, serveTypeId)
                .eq(ServeItemDO::getActiveStatus, HousekeepingStatusEnum.ENABLE.getStatus());
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 分页查询
     *
     * @param serveItemPageRequsetDTO 查询条件
     * @return 分页结果
     */
    @Override
    public PageDTO<ServeItemDTO> page(ServeItemPageRequest serveItemPageRequsetDTO) {
        Page<ServeItemDO> serveItemDOPage = new Page<>(serveItemPageRequsetDTO.getPageNo(), serveItemPageRequsetDTO.getPageSize());
        Page<ServeItemDO> serveItemDOIPage = baseMapper.queryList(serveItemPageRequsetDTO.getServeTypeId()
                , serveItemPageRequsetDTO.getName(), serveItemPageRequsetDTO.getActiveStatus(), serveItemDOPage);
        List<ServeItemDTO> serveItemDTOs = serveItemConverter.serveItemDOsToServeItemDTOs(serveItemDOIPage.getRecords());
        PageDTO<ServeItemDTO> serveItemDTOPageDTO = PageUtils.toPage(serveItemDOIPage,serveItemDTOs);
        return serveItemDTOPageDTO;
    }

    /**
     * 根据id查询
     *
     * @param id 服务项id
     * @return 服务项详细信息
     */
    @Override
    public ServeItemDTO queryServeItemAndTypeById(Long id) {
        ServeItemDO serveItemDO = baseMapper.queryServeItemAndTypeById(id);
        ServeItemDTO serveItemDTO = serveItemConverter.serveItemDOToServeItemDTO(serveItemDO);
        return serveItemDTO;
    }

    /**
     * 根据id列表批量查询
     *
     * @param ids 服务项id列表
     * @return 服务项简略列表
     */
    @Override
    public List<ServeItemSimpleDTO> queryServeItemListByIds(List<Long> ids) {
        List<ServeItemDO> list = lambdaQuery().in(ServeItemDO::getId, ids).orderByAsc(ServeItemDO::getCreateTime).list();
        return serveItemConverter.serveItemDOsToServeItemSimpleDTOs(list);
    }


    /**
     * 查询启用状态的服务项目录
     *
     * @return 服务项目录
     */
    @Override
    public List<ServeTypeInfoDTO> queryActiveServeItemCategory() {
        List<ServeTypeInfoDO> serveTypeInfoDOs = baseMapper.queryActiveServeItemCategory();
        return serveTypeConverter.serveTypeInfoDOs2DTOs(serveTypeInfoDOs);
    }

    @Override
    public void refreshServeItemCache(Long id) {
        // 查询该服务项对应的所有区域的serveId

        ServeItemDO byId = getById(id);

        LambdaQueryWrapper<RegionServeDO> regionServeWrapper = Wrappers.<RegionServeDO>lambdaQuery()
                .eq(RegionServeDO::getServeItemId, id)
                .select(RegionServeDO::getId);


        regionServeMapper.selectList(regionServeWrapper).forEach(regionServeDO -> {
            redissonClient.getBucket(RedisConstants.CacheName.REGION_SERVE_DETAIL + regionServeDO.getId()).delete();
        });
    }
}
