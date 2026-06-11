package com.cskaoyan.duolai.clean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 经纬度
 *
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationResDTO {
    /**
     * 经纬度
     */
    private String location;
}
