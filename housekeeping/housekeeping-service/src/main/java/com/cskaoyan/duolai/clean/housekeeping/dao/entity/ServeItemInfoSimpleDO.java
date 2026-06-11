package com.cskaoyan.duolai.clean.housekeeping.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 服务项简略响应值
 **/
@Data
@ApiModel("服务项简略响应值")
public class ServeItemInfoSimpleDO {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long serveItemId;

    /**
     * 服务名称
     */
    @ApiModelProperty("服务名称")
    private String serveItemName;
}
