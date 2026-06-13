package com.cskaoyan.duolai.clean.user.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 服务技能新增请求
 **/
@Data
@ApiModel("服务技能新增请求")
public class ServeSkillCommand {

    /**
     * 服务类型id
     */
    @ApiModelProperty(value = "服务类型id", required = true)
    private Long serveTypeId;

    /**
     * 服务类型名称
     */
    @ApiModelProperty(value = "服务类型名称", required = true)
    private String serveTypeName;

    /**
     * 服务项id
     */
    @ApiModelProperty(value = "服务项id", required = true)
    private Long serveItemId;

    /**
     * 服务项名称
     */
    @ApiModelProperty(value = "服务项名称", required = true)
    private String serveItemName;
}
