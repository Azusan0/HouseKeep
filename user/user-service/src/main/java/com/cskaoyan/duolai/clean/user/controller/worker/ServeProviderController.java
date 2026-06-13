package com.cskaoyan.duolai.clean.user.controller.worker;

import com.cskaoyan.duolai.clean.user.dto.ServeProviderDTO;
import com.cskaoyan.duolai.clean.user.service.IServeProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("workerServeProviderController")
@RequestMapping("/worker/serve-provider")
@Api(tags = "服务端 - 服务人员相关接口")
public class ServeProviderController {

    @Resource
    private IServeProviderService serveProviderService;

    @GetMapping("/currentUserInfo")
    @ApiOperation("获取当前用户信息")
    public ServeProviderDTO currentUserInfo() {
        return serveProviderService.currentUserInfo();
    }
}
