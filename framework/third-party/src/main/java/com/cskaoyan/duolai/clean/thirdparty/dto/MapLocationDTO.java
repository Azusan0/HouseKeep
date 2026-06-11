package com.cskaoyan.duolai.clean.thirdparty.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地图定位信息
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapLocationDTO {
    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 详细地址
     */
    private String fullAddress;
}
