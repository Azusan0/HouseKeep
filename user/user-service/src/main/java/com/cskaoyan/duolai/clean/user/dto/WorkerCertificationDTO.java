package com.cskaoyan.duolai.clean.user.dto;

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
 * 服务人员认证信息表
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WorkerCertificationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务人员id
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 身份证正面
     */
    private String frontImg;

    /**
     * 身份证反面
     */
    private String backImg;

    /**
     * 证明资料
     */
    private String certificationMaterial;

    /**
     * 认证状态，0：初始态，1：认证中，2：认证成功，3认证失败
     */
    private Integer certificationStatus;

    /**
     * 认证时间
     */
    private LocalDateTime certificationTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
