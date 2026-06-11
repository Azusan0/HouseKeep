package com.cskaoyan.duolai.clean.housekeeping.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 服务项简略响应值
 **/
@Data
@ApiModel("服务项简略响应值")
public class ServeItemSimpleDTO {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 服务名称
     */
    @ApiModelProperty("服务名称")
    private String name;
}
