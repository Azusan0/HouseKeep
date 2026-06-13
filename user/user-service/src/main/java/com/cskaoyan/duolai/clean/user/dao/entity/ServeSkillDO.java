package com.cskaoyan.duolai.clean.user.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 服务技能表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("serve_skill")
public class ServeSkillDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 服务人员id
     */
    private Long serveProviderId;


    /**
     * 服务类型id
     */
    private Long serveTypeId;

    /**
     * 服务类型名称
     */
    private String serveTypeName;

    /**
     * 服务项id
     */
    private Long serveItemId;

    /**
     * 服务项名称
     */
    private String serveItemName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
