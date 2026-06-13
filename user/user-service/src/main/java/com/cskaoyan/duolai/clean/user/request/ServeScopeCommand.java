package com.cskaoyan.duolai.clean.user.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  “” 86188
 */
@Data
@ApiModel("服务范围设置模型")
public class ServeScopeCommand {
    /**
     * 城市编码
     */
    @ApiModelProperty(value = "城市编码", required = true)
    private String cityCode;

    @ApiModelProperty(value = "城市名称", required = true)
    private String cityName;
    /**
     * 坐标经纬度，例如经度,纬度
     */
    @ApiModelProperty(value = "坐标经纬度，例如经度,纬度", required = true)
    private String location;
    /**
     * 意向接单范围
     */
    @ApiModelProperty(value = "接单地点的名称", required = true)
    private String intentionScope;
}
