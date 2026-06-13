package com.cskaoyan.duolai.clean.user.dao.entity;

import lombok.Data;

/**
 * 服务技能简略信息
 *
 **/
@Data
public class ServeSkillSimpleDO {
    /**
     * 服务类型名称
     */
    private String serveTypeName;

    /**
     * 服务项名称
     */
    private String serveItemName;
}
