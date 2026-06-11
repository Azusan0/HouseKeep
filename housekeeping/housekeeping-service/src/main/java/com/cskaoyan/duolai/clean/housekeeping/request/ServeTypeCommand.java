package com.cskaoyan.duolai.clean.housekeeping.request;

import lombok.Data;


/**
 * 服务类型新增更新
 **/
@Data
public class ServeTypeCommand {
    /**
     * 服务类型名称
     */
    private String name;

    /**
     * 服务类型图标
     */
    private String serveTypeIcon;

    /**
     * 服务类型图片
     */
    private String img;

    /**
     * 排序字段
     */
    private Integer sortNum;
}
