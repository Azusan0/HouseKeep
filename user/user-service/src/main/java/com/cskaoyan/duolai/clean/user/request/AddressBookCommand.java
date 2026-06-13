package com.cskaoyan.duolai.clean.user.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 地址薄新增更新
 * </p>
 *
 */
@Data
@ApiModel("地址薄新增更新")
public class AddressBookCommand {

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话", required = true)
    private String phone;

    /**
     * 省份
     */
    @ApiModelProperty(value = "省份", required = true)
    private String province;

    /**
     * 市级
     */
    @ApiModelProperty(value = "市级", required = true)
    private String city;

    /**
     * 区/县
     */
    @ApiModelProperty(value = "区/县", required = true)
    private String county;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址", required = true)
    private String address;

    /**
     * 是否为默认地址，0：否，1：是
     */
    @ApiModelProperty(value = "是否为默认地址，0：否，1：是", required = true)
    private Integer isDefault;

    /**
     * 经纬度
     */
    @ApiModelProperty(value = "经纬度")
    private String location;
}
