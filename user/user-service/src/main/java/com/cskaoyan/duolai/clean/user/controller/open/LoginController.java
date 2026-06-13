package com.cskaoyan.duolai.clean.user.controller.open;

import com.cskaoyan.duolai.clean.user.request.LoginForCustomerCommand;
import com.cskaoyan.duolai.clean.user.request.LoginForWorkCommand;
import com.cskaoyan.duolai.clean.user.dto.LoginDTO;
import com.cskaoyan.duolai.clean.user.service.ILoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("openLoginController")
@RequestMapping("/open/login")
@Api(tags = "白名单接口 - 客户登录相关接口")
public class LoginController {

    @Resource
    private ILoginService loginService;

    @PostMapping("/worker")
    @ApiOperation("服务人员登录接口")
    public LoginDTO loginForWorker(@RequestBody LoginForWorkCommand loginForWorkCommand) {
        return loginService.loginForWorker(loginForWorkCommand);
    }

    /**
     * c端用户登录接口
     */
    @PostMapping("/common/user")
    @ApiOperation("c端用户登录接口")
    public LoginDTO loginForCommonUser(@RequestBody LoginForCustomerCommand loginForCustomerCommand) {
        return loginService.loginForCommonUser(loginForCustomerCommand);
    }

}
