package com.cskaoyan.duolai.clean.housekeeping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.RegionSimpleDTO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.RegionDO;
import com.cskaoyan.duolai.clean.housekeeping.request.RegionCommand;
import com.cskaoyan.duolai.clean.housekeeping.request.RegionPageRequest;
import com.cskaoyan.duolai.clean.housekeeping.dto.RegionDTO;

import java.util.List;

/**
 * 区域管理
 **/
public interface IRegionService extends IService<RegionDO> {

    /**
     * 区域新增
     *
     * @param regionCommand 插入更新区域
     */
    void addRegion(RegionCommand regionCommand);

    /**
     * 区域修改
     *
     * @param id           区域id
     * @param managerName  负责人姓名
     * @param managerPhone 负责人电话
     */
    void update(Long id, String managerName, String managerPhone);

    /**
     * 区域删除
     *
     * @param id 区域id
     */
    void deleteById(Long id);

    /**
     * 分页查询
     *
     * @param regionPageQueryReqDTO 查询条件
     * @return 分页结果
     */
    PageDTO<RegionDTO> getPage(RegionPageRequest regionPageQueryReqDTO);

    /**
     * 已开通服务区域列表
     *
     * @return 区域列表
     */
    List<RegionSimpleDTO> queryActiveRegionListDb();

    /**
     * 区域启用, 该方法返回值在讲缓存的时候才会用到，在讲缓存之前返回null即可
     *
     * @param id 区域id
     */
    List<RegionSimpleDTO> active(Long id);

    /**
     * 区域禁用, 该方法返回值在讲缓存的时候才会用到，在讲缓存之前返回null即可
     *
     * @param id 区域id
     */
    List<RegionSimpleDTO> deactivate(Long id);

    /**
     * 已开通服务区域列表
     *
     * @return 区域简略列表
     */
    List<RegionSimpleDTO> queryActiveRegionListCache();

    /**
     * 刷新区域id相关缓存：首页图标、热门服务、服务分类
     *
     * @param regionId 区域id
     */
    void refreshRegionRelateCaches(Long regionId);

}
