package com.cskaoyan.duolai.clean.user.dao.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServeProviderOperationDO {
    /**
     * 服务人员id
     */
    private  Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 身份证号/统一社会信用代码
     */
    private String idNumber;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 是否可以接单，0：关闭接单，1：开启接单
     */
    private Integer canPickUp;

    /**
     * 状态，0：正常，1：冻结
     */
    private Integer status;

    /**
     * 认证时间
     */
    private LocalDateTime certificationTime;
}
