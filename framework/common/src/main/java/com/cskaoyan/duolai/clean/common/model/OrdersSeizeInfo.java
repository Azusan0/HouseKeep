package com.cskaoyan.duolai.clean.common.model;

import com.cskaoyan.duolai.clean.common.model.Location;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 用于向ES存储抢单池信息
 */
@Data
@Document(indexName = "orders_seize")
public class OrdersSeizeInfo {
    /**
     * 抢单id，和订单id保持一致
     */
    @Id
    private Long id;

    /**
     * 城市编码
     */
    @Field(type = FieldType.Keyword)
    private String cityCode;
    /**
     * 分类id
     */
    @Field(type = FieldType.Long)
    private Long serveTypeId;
    /**
     * 服务项id
     */
    @Field(type = FieldType.Long)
    private Long serveItemId;

    /**
     * 服务类型名称
     */
    @Field(type = FieldType.Text, index = false)
    private String serveTypeName;

    /**
     * 服务项名称
     */
    @Field(type = FieldType.Text, index = false)
    private String serveItemName;

    /**
     * 服务地址
     */
    @Field(type = FieldType.Text, index = false)
    private String serveAddress;

    /**
     * 地理坐标，经纬度
     */
    @GeoPointField
    private Location location;

    /**
     * 服务开始时间
     */
    @Field(type = FieldType.Date, index = false, format = DateFormat.date_hour_minute_second)
    private LocalDateTime serveStartTime;

    /**
     * 服务数量
     */
    @Field(type = FieldType.Integer)
    private Integer purNum;

    /**
     * 订单总金额
     */
    @Field(type = FieldType.Double)
    private Double totalAmount;

    /**
     * 订单金额
     */
    @Field(type = FieldType.Double)
    private BigDecimal ordersAmount;

    /**
     * 服务项图片
     */
    @Field(type = FieldType.Text, index = false)
    private String serveItemImg;

}
