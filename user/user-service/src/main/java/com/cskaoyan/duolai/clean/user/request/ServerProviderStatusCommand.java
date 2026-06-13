package com.cskaoyan.duolai.clean.user.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 服务人员更新状态请求
 **/
@Data
@ApiModel("服务人员更新状态请求")
public class ServerProviderStatusCommand {
    /**
     * 服务人员id
     */
    @ApiModelProperty(value = "服务人员id", required = true)
    private Long id;

    /**
     * 状态，0：正常，1：冻结
     */
    @ApiModelProperty(value = "状态，0：正常，1：冻结", required = true)
    private Integer status;

    /**
     * 账号冻结原因
     */
    @ApiModelProperty("账号冻结原因")
    private String accountLockReason;
}
