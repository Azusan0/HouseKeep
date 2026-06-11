package com.cskaoyan.duolai.clean.housekeeping.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.ServeItemDO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.ServeTypeInfoDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 服务表 Mapper 接口
 * </p>
 */
public interface ServeItemMapper extends BaseMapper<ServeItemDO> {

    /**
     * 根据条件查询服务项列表
     *
     * @param serveTypeId  服务类型id
     * @param name         服务项名称
     * @param activeStatus 活动状态，0：草稿，1禁用，2启用
     * @return 服务项列表
     */
    Page<ServeItemDO> queryList(@Param("serveTypeId") Long serveTypeId, @Param("name") String name, @Param("activeStatus") Integer activeStatus, @Param("pageParam") Page<ServeItemDO> pageParam);

    /**
     * 根据id查询服务项和服务类型信息
     *
     * @param id 服务项id
     * @return 服务项和服务类型信息
     */
    ServeItemDO queryServeItemAndTypeById(@Param("id") Long id);

    /**
     * 查询启用状态的服务项目录
     *
     * @return 服务项目录
     */
    List<ServeTypeInfoDO> queryActiveServeItemCategory();
}
