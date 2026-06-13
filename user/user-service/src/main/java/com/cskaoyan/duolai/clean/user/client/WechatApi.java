package com.cskaoyan.duolai.clean.user.client;

import com.cskaoyan.duolai.clean.dto.OpenIdResDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "foundation", contextId = "foundation-wechat", path = "/foundation/inner/wechat")
public interface WechatApi {

    @GetMapping("/getOpenId")
    OpenIdResDTO getOpenId(@RequestParam("code") String code);
}
