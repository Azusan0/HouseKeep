package com.cskaoyan.duolai.clean.housekeeping.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 服务简略响应信息
 **/
@Data
@ApiModel("服务简略响应信息")
public class SearchRegionServeDTO {

    /**
     * RegionServe服务id
     */
    @ApiModelProperty("服务id")
    private Long id;

    /**
     * 服务项id
     */
    @ApiModelProperty("服务项id")
    private Long serveItemId;

    /**
     * 服务项名称
     */
    @ApiModelProperty("服务项名称")
    private String serveItemName;

    /**
     * 服务项图标
     */
    @ApiModelProperty("服务项图标")
    private String serveItemIcon;

    /**
     * 服务项排序字段
     */
    @ApiModelProperty("服务项排序字段")
    private Integer serveItemSortNum;
}
