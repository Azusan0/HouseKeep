package com.cskaoyan.duolai.clean.common.model;

import cn.hutool.core.collection.CollUtil;
import com.cskaoyan.duolai.clean.common.expcetions.CommonException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ApiModel(value = "分页数据消息体", description = "分页数据统一对象")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数", required = true)
    private Long pages = 0L;

    /**
     * 总条数
     */
    @ApiModelProperty(value = "总条数", required = true)
    private Long total;

    /**
     * 数据列表
     */
    @ApiModelProperty(value = "数据列表", required = true)
    private List<T> list = Collections.EMPTY_LIST;


    /**
     * 返回一个分页对象实例
     *
     * @return 分页数据对象
     */
    public static <T> PageResult<T> getInstance() {
        return PageResult.<T>builder().build();
    }

    





}
