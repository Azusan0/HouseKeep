package com.cskaoyan.duolai.clean.user.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("common_user")
public class CommonUserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 状态，0：正常，1：冻结
     */
    private Integer status;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 电话
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * openId
     */
    private String openId;

    /**
     * 账号冻结原因
     */
    private String accountLockReason;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
