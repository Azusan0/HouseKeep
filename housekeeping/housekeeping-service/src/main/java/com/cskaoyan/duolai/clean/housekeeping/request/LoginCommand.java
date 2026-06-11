package com.cskaoyan.duolai.clean.housekeeping.request;

import lombok.Data;

@Data
public class LoginCommand {

    /**
     * 运营人员账号
     */
    private String username;
    /**
     * 登录密码
     */
    private String password;
}
