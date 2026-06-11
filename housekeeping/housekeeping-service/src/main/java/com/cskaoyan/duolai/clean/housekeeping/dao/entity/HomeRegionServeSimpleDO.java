package com.cskaoyan.duolai.clean.housekeeping.dao.entity;


import lombok.Data;

/**
 * 服务简略响应信息
 **/
@Data
public class HomeRegionServeSimpleDO {

    /**
     * 服务id
     */
    private Long id;

    /**
     * 服务项id
     */
    private Long serveItemId;

    /**
     * 服务项名称
     */
    private String serveItemName;

    /**
     * 服务项图标
     */
    private String serveItemIcon;

    /**
     * 服务项排序字段
     */
    private Integer serveItemSortNum;
}
