package com.cskaoyan.duolai.clean.housekeeping.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cskaoyan.duolai.clean.common.expcetions.ForbiddenOperationException;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.housekeeping.converter.ServeTypeConverter;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeSimpleDTO;
import com.cskaoyan.duolai.clean.housekeeping.enums.HousekeepingStatusEnum;
import com.cskaoyan.duolai.clean.housekeeping.dao.mapper.ServeTypeMapper;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.ServeTypeDO;
import com.cskaoyan.duolai.clean.housekeeping.request.RegionServeSyncDTO;
import com.cskaoyan.duolai.clean.housekeeping.request.ServeTypeCommand;
import com.cskaoyan.duolai.clean.housekeeping.request.ServeTypePageRequest;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeDTO;
import com.cskaoyan.duolai.clean.housekeeping.service.IServeItemService;
import com.cskaoyan.duolai.clean.housekeeping.service.IServeSyncService;
import com.cskaoyan.duolai.clean.housekeeping.service.IServeTypeService;
import com.cskaoyan.duolai.clean.mysql.utils.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 服务类型
 **/
@Service
public class ServeTypeServiceImpl extends ServiceImpl<ServeTypeMapper, ServeTypeDO> implements IServeTypeService {
    @Resource
    private IServeItemService serveItemService;
    @Resource
    private IServeSyncService serveSyncService;

    @Resource
    ServeTypeConverter categoryConverter;

    /**
     * 服务类型新增
     */
    @Override
    public void addServeType(ServeTypeCommand serveTypeCommand) {
        //校验名称是否重复
        LambdaQueryWrapper<ServeTypeDO> queryWrapper = Wrappers.<ServeTypeDO>lambdaQuery().eq(ServeTypeDO::getName, serveTypeCommand.getName());
        Long count = baseMapper.selectCount(queryWrapper);
        if(count>0){
            throw new ForbiddenOperationException("服务类型名称不可重复");
        }
        //新增服务类型
        ServeTypeDO serveTypeDO = categoryConverter.serverTypeCommandToServeTypeDO(serveTypeCommand);
        serveTypeDO.setCode(IdUtil.getSnowflakeNextIdStr());
        baseMapper.insert(serveTypeDO);
    }

    /**
     * 服务类型修改
     *
     * @param id                    服务类型id
     * @param serveTypeCommand 插入更新服务类型
     */
    @Override
    public void updateServeType(Long id, ServeTypeCommand serveTypeCommand) {

        ServeTypeDO oldServeType = baseMapper.selectById(id);
        if (HousekeepingStatusEnum.INIT.getStatus() != oldServeType.getActiveStatus()
                && HousekeepingStatusEnum.DISABLE.getStatus() != oldServeType.getActiveStatus()) {
            throw new ForbiddenOperationException("草稿状态或者禁用状态方可修改");
        }

        //1.更新服务类型
        ServeTypeDO serveTypeDO = categoryConverter
                .serverTypeCommandToServeTypeDO(serveTypeCommand);
        serveTypeDO.setId(id);
        baseMapper.updateById(serveTypeDO);

        //2.同步数据到es
        RegionServeSyncDTO regionServeSyncDTO = new RegionServeSyncDTO();
        regionServeSyncDTO.setServeTypeName(serveTypeCommand.getName());
        regionServeSyncDTO.setServeTypeImg(serveTypeCommand.getImg());
        regionServeSyncDTO.setServeTypeIcon(serveTypeCommand.getServeTypeIcon());
        regionServeSyncDTO.setServeTypeSortNum(serveTypeCommand.getSortNum());
        serveSyncService.updateByServeTypeId(id, regionServeSyncDTO);
    }


    /**
     * 服务类型启用/禁用
     *
     * @param id 服务类型id
     */
    @Override
    @Transactional
    public void activateServeType(Long id) {
        //查询服务类型
        ServeTypeDO serveTypeDO = baseMapper.selectById(id);
        if (ObjectUtil.isNull(serveTypeDO)) {
            throw new ForbiddenOperationException("服务类型不存在");
        }
        //启用状态
        Integer activeStatus = serveTypeDO.getActiveStatus();
        //草稿或禁用状态方可启用
        if (!(HousekeepingStatusEnum.INIT.getStatus() == activeStatus || HousekeepingStatusEnum.DISABLE.getStatus() == activeStatus)) {
            throw new ForbiddenOperationException("草稿或禁用状态方可启用");
        }
        //更新状态为启用
        LambdaUpdateWrapper<ServeTypeDO> updateWrapper = Wrappers.<ServeTypeDO>lambdaUpdate()
                .eq(ServeTypeDO::getId, id)
                .set(ServeTypeDO::getActiveStatus, HousekeepingStatusEnum.ENABLE.getStatus());
        update(updateWrapper);
    }

    /**
     * 服务类型启用/禁用
     *
     * @param id 服务类型id
     */
    @Override
    @Transactional
    public void deactivateServeType(Long id) {
        //查询服务类型
        ServeTypeDO serveTypeDO = baseMapper.selectById(id);
        if (ObjectUtil.isNull(serveTypeDO)) {
            throw new ForbiddenOperationException("服务类型不存在");
        }
        //启用状态
        Integer activeStatus = serveTypeDO.getActiveStatus();
        //启用状态方可禁用
        if (!(HousekeepingStatusEnum.ENABLE.getStatus() == activeStatus)) {
            throw new ForbiddenOperationException("启用状态方可禁用");
        }
        //下属服务项全部为非启用方可禁用
        Long count = serveItemService.queryActiveTypeItemCount(id);
        if (count > 0) {
            throw new ForbiddenOperationException("禁用失败，该服务类型下有启用状态的服务项");
        }
        //更新状态为禁用
        LambdaUpdateWrapper<ServeTypeDO> updateWrapper = Wrappers.<ServeTypeDO>lambdaUpdate()
                .eq(ServeTypeDO::getId, id)
                .set(ServeTypeDO::getActiveStatus, HousekeepingStatusEnum.DISABLE.getStatus());
        update(updateWrapper);
    }

    /**
     * 根据id删除服务类型
     *
     * @param id 服务类型id
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        //查询服务类型
        ServeTypeDO serveTypeDO = baseMapper.selectById(id);
        if (ObjectUtil.isNull(serveTypeDO)) {
            throw new ForbiddenOperationException("服务类型不存在");
        }
        //启用状态
        Integer activeStatus = serveTypeDO.getActiveStatus();
        //草稿状态方可删除
        if (!(HousekeepingStatusEnum.INIT.getStatus() == activeStatus)) {
            throw new ForbiddenOperationException("草稿状态方可删除");
        }
        baseMapper.deleteById(id);
    }

    /**
     * 分页查询
     *
     * @param serveTypePageRequest 查询条件
     * @return 分页结果
     */
    @Override
    public PageDTO<ServeTypeDTO> getPage(ServeTypePageRequest serveTypePageRequest) {
        Page<ServeTypeDO> page = PageUtils.parsePageQuery(serveTypePageRequest, ServeTypeDO.class);
        Page<ServeTypeDO> serveTypePage = baseMapper.selectPage(page, new QueryWrapper<>());
        List<ServeTypeDTO> serveTypeHomeDTOS = categoryConverter.serveTypeToSimpleResDTOs(serveTypePage.getRecords());
        return PageUtils.toPage(page, serveTypeHomeDTOS);
    }

    /**
     * 根据活动状态查询简略列表
     *
     * @param activeStatus 活动状态，0：草稿，1：禁用，:2：启用
     * @return 服务类型列表
     */
    @Override
    public List<ServeTypeSimpleDTO> activeList(Integer activeStatus) {
        LambdaQueryWrapper<ServeTypeDO> queryWrapper = Wrappers.<ServeTypeDO>lambdaQuery()
                .eq(ObjectUtil.isNotEmpty(activeStatus), ServeTypeDO::getActiveStatus, activeStatus)
                .orderByAsc(ServeTypeDO::getSortNum)
                .orderByDesc(ServeTypeDO::getUpdateTime);
        List<ServeTypeDO> serveTypeDOList = baseMapper.selectList(queryWrapper);
        return categoryConverter.serveTypesToSimpleDTOs(serveTypeDOList);
    }
}
