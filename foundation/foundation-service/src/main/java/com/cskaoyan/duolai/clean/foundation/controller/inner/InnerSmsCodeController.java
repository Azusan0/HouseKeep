package com.cskaoyan.duolai.clean.foundation.controller.inner;

import com.cskaoyan.duolai.clean.dto.BooleanResDTO;
import com.cskaoyan.duolai.clean.foundation.service.ISmsCodeService;
import com.cskaoyan.duolai.clean.common.enums.SmsBussinessTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * 验证码
 **/
@RestController
@RequestMapping("/inner/sms-code")
@Api(tags = "内部接口 - 验证码相关接口")
public class InnerSmsCodeController {
    @Resource
    private ISmsCodeService smsCodeService;


    @GetMapping("/verify")
    @ApiOperation("校验短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "验证手机号", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "bussinessType", value = "业务类型", required = true, dataTypeClass = SmsBussinessTypeEnum.class),
            @ApiImplicitParam(name = "verifyCode", value = "验证码", required = true, dataTypeClass = String.class)
    })
    public BooleanResDTO verify(@RequestParam("phone") String phone,
                                @RequestParam("bussinessType") SmsBussinessTypeEnum bussinessType,
                                @RequestParam("verifyCode") String verifyCode) {

        return new BooleanResDTO(smsCodeService.verify(phone, bussinessType, verifyCode));
    }
}
