package com.cskaoyan.duolai.clean.common.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(value = "分页数据消息体", description = "分页数据统一对象")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> {
    /**
     * 总页数
     */
    private Long pages = 0L;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 数据列表
     */
    private List<T> list;

}
