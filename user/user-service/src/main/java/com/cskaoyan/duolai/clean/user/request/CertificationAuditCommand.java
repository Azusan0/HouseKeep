package com.cskaoyan.duolai.clean.user.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 认证信息审核请求体
 **/
@Data
@ApiModel("认证信息审核请求体")
public class CertificationAuditCommand {
    @ApiModelProperty(value = "认证状态，2：认证成功，3认证失败", required = true)
    private Integer certificationStatus;

    @ApiModelProperty(value = "驳回原因", required = false)
    private String rejectReason;
}
