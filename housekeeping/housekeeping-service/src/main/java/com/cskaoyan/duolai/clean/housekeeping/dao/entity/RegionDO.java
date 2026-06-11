package com.cskaoyan.duolai.clean.housekeeping.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 区域表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("region")
public class RegionDO implements Serializable {
    private static final long serialVersionUID = -6475795569304770481L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 区域名称
     */
    private String name;

    /**
     * 负责人名称
     */
    private String managerName;

    /**
     * 负责人电话
     */
    private String managerPhone;

    /**
     * 活动状态，0：草稿，1：禁用，:2：启用
     */
    private Integer activeStatus;

    /**
     * 排序字段
     */
    private Integer sortNum;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
}
