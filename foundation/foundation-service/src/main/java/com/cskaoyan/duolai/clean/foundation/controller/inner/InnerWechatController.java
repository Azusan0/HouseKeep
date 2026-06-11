package com.cskaoyan.duolai.clean.foundation.controller.inner;

import com.cskaoyan.duolai.clean.dto.OpenIdResDTO;
import com.cskaoyan.duolai.clean.thirdparty.core.wechat.WechatService;
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
 * 微信服务
 **/
@RestController
@RequestMapping("/inner/wechat")
@Api(tags = "内部接口 - 微信服务相关接口")
public class InnerWechatController {

    @Resource
    private WechatService wechatService;

    @GetMapping("/getOpenId")
    @ApiOperation("获取openId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "登录凭证", required = true, dataTypeClass = String.class)
    })
    public OpenIdResDTO getOpenId(@RequestParam("code") String code) {
        String openId = wechatService.getOpenid(code);
        return new OpenIdResDTO(openId);
    }

}
