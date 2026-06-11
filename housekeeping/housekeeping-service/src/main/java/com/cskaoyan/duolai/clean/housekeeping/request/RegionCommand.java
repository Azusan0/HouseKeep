package com.cskaoyan.duolai.clean.housekeeping.request;

import lombok.Data;

/**
 * 区域新增更新
 *
 **/
@Data
public class RegionCommand {

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
}
