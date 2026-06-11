package com.cskaoyan.duolai.clean.housekeeping.dao.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 服务响应值
 *
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionServeDetailDO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 服务id
     */
    private Long serveItemId;

    /**
     * 服务项名称
     */
    private String serveItemName;

    /**
     * 服务项图片
     */
    private String serveItemImg;

    /**
     * 服务单位
     */
    private Integer unit;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 服务详图
     */
    private String detailImg;

    /**
     * 城市编码
     */
    private String cityCode;

    private Long regionId;
}
