package com.cskaoyan.duolai.clean.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel("登录结果")
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @ApiModelProperty("访问token")
    private String token;
}
