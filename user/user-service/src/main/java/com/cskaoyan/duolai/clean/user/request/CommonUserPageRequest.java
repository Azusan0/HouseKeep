package com.cskaoyan.duolai.clean.user.request;

import com.cskaoyan.duolai.clean.common.model.dto.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户分页查询请求")
public class CommonUserPageRequest extends PageRequest {
    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;

    /**
     * 电话
     */
    @ApiModelProperty("电话")
    private String phone;
}
