package com.cskaoyan.duolai.clean.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 城市编码响应信息
 **/
@Data
@ApiModel("城市编码响应信息")
public class CityCodeResDTO {

    /**
     * 城市名称
     */
    @ApiModelProperty("城市名称")
    private String cityName;

    /**
     * 城市编码
     */
    @ApiModelProperty("城市编码")
    private String cityCode;
}
