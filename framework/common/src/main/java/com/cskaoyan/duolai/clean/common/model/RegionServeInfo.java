package com.cskaoyan.duolai.clean.common.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

// Index = goods , Type = info  es 7.8.0 逐渐淡化type！  修改！
@Data
@Document(indexName = "region_serve_info")
public class RegionServeInfo {
    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String cityCode;

    //  es 中能分词的字段，这个字段数据类型必须是 text！keyword 不分词！
    @Field(type = FieldType.Text, index = false)
    private String detailImg;

    @Field(type = FieldType.Short)
    private Short isHot;

    @Field(type = FieldType.Long)
    private Long hotTimeStamp;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Text, index = false)
    private String serveItemIcon;

    @Field(type = FieldType.Keyword)
    private String serveItemId;

    @Field(type = FieldType.Text, index = false)
    private String serveItemImg;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String serveItemName;

    @Field(type = FieldType.Keyword)
    private String serveItemSortNum;

    @Field(type = FieldType.Keyword)
    private String serveTypeId;

    @Field(type = FieldType.Text, index = false)
    private String serveTypeIcon;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String serveTypeName;

    @Field(type = FieldType.Keyword)
    private String serveTypeSortNum;


}
