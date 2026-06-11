package com.cskaoyan.duolai.clean.housekeeping.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 服务新增更新
 **/
@Data
public class RegionServeCommand {

    /**
     * 服务id
     */
    private Long serveItemId;

    /**
     * 区域id
     */
    private Long regionId;

    /**
     * 价格
     */
    private BigDecimal price;
}
