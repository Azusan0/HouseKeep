package com.cskaoyan.duolai.clean.housekeeping.dto;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * 区域响应值
 **/
@Data
public class RegionDTO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 区域名称
     */
    private String name;

    /**
     * 负责人名称
     */
    private String managerName;

    /**
     * 负责人电话
     */
    private String managerPhone;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 活动状态，0：草稿，1：禁用，:2：启用
     */
    private Integer activeStatus;
}
