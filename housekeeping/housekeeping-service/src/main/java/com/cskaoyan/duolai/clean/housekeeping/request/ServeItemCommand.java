package com.cskaoyan.duolai.clean.housekeeping.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 *   服务项新增更新
 **/
@Data
public class ServeItemCommand {

    /**
     * 服务类型id
     */
    private Long serveTypeId;

    /**
     * 服务名称
     */
    private String name;

    /**
     * 服务图标
     */
    private String serveItemIcon;

    /**
     * 服务图片
     */
    private String img;

    /**
     * 服务单位
     */
    private Integer unit;

    /**
     * 服务描述
     */
    private String description;

    /**
     * 服务详图
     */
    private String detailImg;

    /**
     * 参考价格
     */
    private BigDecimal referencePrice;

    /**
     * 排序字段
     */
    private Integer sortNum;
}
