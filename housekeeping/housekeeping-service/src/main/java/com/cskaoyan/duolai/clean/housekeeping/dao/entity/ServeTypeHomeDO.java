package com.cskaoyan.duolai.clean.housekeeping.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 首页服务图标
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServeTypeHomeDO {

    /**
     * 服务类型id
     */
    private Long serveTypeId;

    /**
     * 服务类型名称
     */
    private String serveTypeName;

    /**
     * 服务类型图标
     */
    private String serveTypeIcon;
    private String cityCode;

    /**
     * 服务类型排序字段
     */
    private Integer serveTypeSortNum;

    /**
     * 服务项图标列表
     */
    private List<HomeRegionServeSimpleDO> serveResDTOList;
}
