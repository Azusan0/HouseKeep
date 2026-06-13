package com.cskaoyan.duolai.clean.user.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务人员
 * </p>
 *
 */
@Data
@ApiModel("服务人员响应数据")
@TableName("serve_provider")
public class ServeProviderDTO {

    /**
     * 主键
     */
    @ApiModelProperty("服务人员id")
    private Long id;

    private String password;

    /**
     * 编号
     */
    @ApiModelProperty("编号")
    private String code;

    /**
     * 类型，2：服务人员，3：服务机构
     */
    @ApiModelProperty("类型，2：服务人员，3：服务机构")
    private Integer type;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String avatar;

    /**
     * 电话
     */
    @ApiModelProperty("电话")
    private String phone;

    /**
     * 状态，0：正常，1：冻结
     */
    @ApiModelProperty("状态，0：正常，1：冻结")
    private Integer status;

    @ApiModelProperty("所在城市编码")
    private String cityCode;

    @ApiModelProperty("设置状态,0:未完成首次设置，1：已完成首次设置")
    private Integer settingsStatus;

    /**
     * 账号冻结原因
     */
    @ApiModelProperty("账号冻结原因")
    private String accountLockReason;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
