package com.cskaoyan.duolai.clean.housekeeping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务分类响应信息
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisplayServeTypeDTO {

    /**
     * 服务类型id
     */
    private Long serveTypeId;

    /**
     * 服务类型名称
     */
    private String serveTypeName;

    /**
     * 服务类型图片
     */
    private String serveTypeImg;

    /**
     * 服务类型排序字段
     */
    private Integer serveTypeSortNum;
}
