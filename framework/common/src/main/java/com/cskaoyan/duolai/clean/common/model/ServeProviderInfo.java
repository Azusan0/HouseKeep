package com.cskaoyan.duolai.clean.common.model;

import com.cskaoyan.duolai.clean.common.model.Location;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import java.lang.annotation.Documented;
import java.util.List;

@Data
@Document(indexName = "serve_provider_info")
public class ServeProviderInfo {
    /**
     * 服务人员或机构同步表
     */
    @Id
    private Long id;

    /**
     * 技能列表
     */
    @Field(type = FieldType.Long)
    private List<Long> serveItemIds;

    /**
     * 经纬度
     */
    @GeoPointField
    private Location location;

    /**
     * 城市编码
     */
    @Field(type = FieldType.Keyword)
    private String cityCode;

    /**
     * 接单开关1，：接单开启，0：接单关闭
     */
    @Field(type = FieldType.Keyword)
    private Integer pickUp;

    /**
     * 评分,默认50分
     */
    private Double evaluationScore;

    /**
     * 服务时间
     */
    @Field(type = FieldType.Long)
    private List<Long> serveTimes;

    /**
     * 接单数
     */
    @Field(type = FieldType.Integer)
    private Integer acceptanceNum;

    /**
     * 首次设置状态，0：未完成，1：已完成设置
     */
    @Field(type = FieldType.Integer)
    private Integer settingStatus;
    /**
     * 状态，0：正常，1：冻结
     */
    @Field(type = FieldType.Integer)
    private Integer status;
}
