package com.cskaoyan.duolai.clean.user.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 评分同步列表
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "serve_provider_sync",autoResultMap = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServeProviderSyncDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务人员或机构同步表
     */
    @TableId(value = "id", type = IdType.NONE)
    private Long id;

    /**
     * 技能列表
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Long> serveItemIds;

    /**
     * 经度
     */
    private Double lon;

    /**
     * 纬度
     */
    private Double lat;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 接单开关1，：接单开启，0：接单关闭
     */
    private Integer pickUp;

    /**
     * 评分,默认50分
     */
    private Double evaluationScore;

    /**
     * 首次设置状态，0：未完成，1：已完成设置
     */
    private Integer settingStatus;

    /**
     * 状态，0：正常，1：冻结
     */
    private Integer status;


}
