package com.cskaoyan.duolai.clean.housekeeping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeSimpleDTO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.ServeTypeDO;
import com.cskaoyan.duolai.clean.housekeeping.request.ServeTypeCommand;
import com.cskaoyan.duolai.clean.housekeeping.request.ServeTypePageRequest;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeDTO;

import java.util.List;
public interface IServeTypeService extends IService<ServeTypeDO> {

    /**
     * 服务类型新增
     *
     * @param serveTypeCommand 插入更新服务类型
     */
    void addServeType(ServeTypeCommand serveTypeCommand);

    /**
     * 服务类型修改
     *
     * @param id                    服务类型id
     * @param serveTypeCommand 插入更新服务类型
     */
    void updateServeType(Long id, ServeTypeCommand serveTypeCommand);


    /**
     * 服务类型启用/禁用
     *
     * @param id 服务类型id
     */
    void activateServeType(Long id);

    /**
     * 服务类型启用/禁用
     *
     * @param id 服务类型id
     */
    void deactivateServeType(Long id);

    /**
     * 根据id删除服务类型
     *
     * @param id 服务类型id
     */
    void deleteById(Long id);

    /**
     * 分页查询
     *
     * @param serveCategoryPageQueryRequest 查询条件
     * @return 分页结果
     */
    PageDTO<ServeTypeDTO> getPage(ServeTypePageRequest serveCategoryPageQueryRequest);

    /**
     * 根据活动状态查询简略列表
     *
     * @param activeStatus 活动状态，0：草稿，1：禁用，:2：启用
     * @return 服务类型列表
     */
    List<ServeTypeSimpleDTO> activeList(Integer activeStatus);
}
