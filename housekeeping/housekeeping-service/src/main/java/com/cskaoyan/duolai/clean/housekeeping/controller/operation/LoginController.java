package com.cskaoyan.duolai.clean.housekeeping.controller.operation;

import com.cskaoyan.duolai.clean.housekeeping.request.LoginCommand;
import com.cskaoyan.duolai.clean.housekeeping.dto.LoginDTO;
import com.cskaoyan.duolai.clean.housekeeping.service.ILoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

@RestController("openLoginController")
@RequestMapping("/open/login")
public class LoginController {

    @Resource
    private ILoginService loginService;

    @PostMapping
    public LoginDTO login(@RequestBody LoginCommand loginCommand) {

        String token=loginService.login(loginCommand);
        LoginDTO loginDTO=new LoginDTO();
        loginDTO.setToken(token);

        return loginDTO;
    }



}
