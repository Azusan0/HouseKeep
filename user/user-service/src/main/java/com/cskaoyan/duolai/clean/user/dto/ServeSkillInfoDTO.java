package com.cskaoyan.duolai.clean.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 服务技能目录响应结果
 **/
@Data
@ApiModel("服务技能目录响应结果")
public class ServeSkillInfoDTO {
    /**
     * 服务类型d
     */
    @ApiModelProperty("服务类型id")
    private Long serveTypeId;

    /**
     * 服务类型名称
     */
    @ApiModelProperty("服务类型名称")
    private String serveTypeName;

    /**
     * 下属服务技能数量
     */
    @ApiModelProperty("下属服务技能数量")
    private Integer count;

    /**
     * 服务技能项列表
     */
    @ApiModelProperty("服务技能项列表")
    private List<ServeSkillItemDTO> serveSkillItemDTOList;
}
