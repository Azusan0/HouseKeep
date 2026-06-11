package com.cskaoyan.duolai.clean.housekeeping.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 服务类型简略响应值
 **/
@Data
@ApiModel("服务类型简略响应值")
public class ServeTypeSimpleDTO {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 服务类型名称
     */
    @ApiModelProperty("服务类型名称")
    private String name;
}
