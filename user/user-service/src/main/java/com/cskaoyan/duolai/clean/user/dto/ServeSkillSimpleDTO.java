package com.cskaoyan.duolai.clean.user.dto;

import lombok.Data;

/**
 * 服务技能简略信息
 *
 **/
@Data
public class ServeSkillSimpleDTO {
    /**
     * 服务类型名称
     */
    private String serveTypeName;

    /**
     * 服务项名称
     */
    private String serveItemName;
}
