package com.cskaoyan.duolai.clean.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("服务人员或机构简单信息模型")
public class ServeProviderSimpleDTO {
    @ApiModelProperty("服务人员或机构id")
    private Long id;
    @ApiModelProperty("服务人员姓名")
    private String name;
    @ApiModelProperty("服务人员或机构手机号")
    private String phone;
}
