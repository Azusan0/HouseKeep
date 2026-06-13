package com.cskaoyan.duolai.clean.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 服务技能项响应结果
 **/
@Data
@ApiModel("服务技能项响应结果")
public class ServeSkillItemDTO {
    /**
     * 服务项id
     */
    @ApiModelProperty("服务项id")
    private Long serveItemId;

    /**
     * 服务项名称
     */
    @ApiModelProperty("服务项名称")
    private String serveItemName;

    /**
     * 是否被选中
     */
    @ApiModelProperty("是否被选中")
    private Boolean isSelected;
}
