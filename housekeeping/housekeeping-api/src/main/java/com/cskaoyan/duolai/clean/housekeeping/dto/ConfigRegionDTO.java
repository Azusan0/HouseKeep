package com.cskaoyan.duolai.clean.housekeeping.dto;

import lombok.Data;

@Data
public class ConfigRegionDTO {

    /**
     * 区域id
     */
    private Long id;
    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * （个体）接单量限制
     */
    private Integer staffReceiveOrderMax;


    /**
     * （个体）服务范围半径
     */
    private Integer staffServeRadius;


    /**
     * 分流间隔（单位分钟），即下单时间与服务预计开始时间的间隔
     */
    private Integer diversionInterval;

    /**
     * 抢单超时时间间隔（单位分钟），从支付成功进入抢单后超过当前时间抢单派单同步进行
     */
    private Integer seizeTimeoutInterval;

    /**
     * 派单策略，1：距离优先策略，2：评分优先策略，3：接单量优先策略
     */
    private Integer dispatchStrategy;

    /**
     * 派单每轮时间间隔,（单位s）
     */
    private Integer dispatchPerRoundInterval;
}
