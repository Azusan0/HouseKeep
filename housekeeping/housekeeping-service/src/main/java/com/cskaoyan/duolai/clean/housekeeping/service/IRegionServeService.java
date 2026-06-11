package com.cskaoyan.duolai.clean.housekeeping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.RegionServeDO;
import com.cskaoyan.duolai.clean.housekeeping.dto.*;
import com.cskaoyan.duolai.clean.housekeeping.request.RegionServeCommand;
import com.cskaoyan.duolai.clean.housekeeping.request.ServePageRequest;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务表 服务类
 * </p>
 */
public interface IRegionServeService extends IService<RegionServeDO> {

    /**
     * 区域服务分页查询
     * @param servePageQueryReqDTO
     * @return
     */
    PageDTO<RegionServeDTO> getPage(ServePageRequest servePageQueryReqDTO);


    /**
     * 批量新增
     *
     * @param regionServeCommandList 批量新增数据
     */
    void batchAdd(List<RegionServeCommand> regionServeCommandList);

    /**
     * 服务修改, 返回值和首页缓存有关，在没有实现缓存前先返回null
     *
     * @param id    服务id
     * @param price 价格
     * @return 服务
     * 返回修改后的区域服务详情
     */
    RegionServeDetailDTO updatePrice(Long id, Long regionId, BigDecimal price);

    /**
     * 服务设置热门/取消，返回值和首页缓存有关，在没有实现缓存前先返回null
     *
     * @param id   服务id
     * @param flag 是否为热门，0：非热门，1：热门
     * 返回 区域id
     */
    List<RegionServeDetailDTO> changeHotStatus(Long id, Long regionId, Integer flag);

    /**
     * 根据区域id和售卖状态查询关联服务数量
     *
     * @param regionId   区域id
     * @param saleStatus 售卖状态，0：草稿，1下架，2上架。可传null，即查询所有状态
     * @return 服务数量
     */
    int queryServeCountByRegionIdAndSaleStatus(Long regionId, Integer saleStatus);

    /**
     * 根据服务项id和售卖状态查询关联服务数量
     *
     * @param  serveItemId  服务项id
     * @param saleStatus 售卖状态，0：草稿，1下架，2上架。可传null，即查询所有状态
     * @return 服务数量
     */
    int queryServeCountByServeItemIdAndSaleStatus(Long serveItemId, Integer saleStatus);


    /**
     * 删除服务
     *
     * @param id 服务id
     */
    void deleteById(Long id);


    /**
     * 根据id查询服务详情
     *
     * @param id 服务id
     * @return 服务详情
     */
    RegionServeDetailDTO findDetailByIdCache(Long id);


    /**
     * 根据区域id查询热门服务列表
     *
     * @param regionId 区域id
     * @return 热门服务列表
     */
    List<RegionServeDetailDTO> findHotServeListByRegionIdDb(Long regionId);



    /**
     * 上架，返回值和首页缓存有关，在没有实现缓存前先返回null
     *
     * @param id         服务id
     */
    RegionServeDetailDTO onSale(Long id);
    /**
     * 下架， 返回值和首页缓存有关，在没有实现缓存前先返回null
     *
     * @param id         服务id
     */
    RegionServeDTO offSale(Long id);

    /**
     * 根据id获从缓存查询详情(服务调用中使用)
     *
     * @param id 服务id
     * @return 服务详情
     */
    ServeDetailDTO findServeDetailById(Long id);

    /**
     * 根据id获从数据库查询详情
     *
     * @param id 服务id
     * @return 服务详情
     */

    RegionServeDetailDTO findDetailByIdDb(Long id);

    List<ServeTypeHomeDTO> refreshFirstPageRegionServeList(Long regionId);

    List<RegionServeDetailDTO> refreshFistPageHotServeList(Long regionId);


    List<DisplayServeTypeDTO> refreshFirstPageServeTypeList(Long regionId);

}
