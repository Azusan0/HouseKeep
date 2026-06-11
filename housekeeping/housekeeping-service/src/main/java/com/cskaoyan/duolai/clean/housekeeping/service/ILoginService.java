package com.cskaoyan.duolai.clean.housekeeping.service;


import com.cskaoyan.duolai.clean.housekeeping.request.LoginCommand;

/**
 * 登录相关业务
 *  “”  
 */
public interface ILoginService {
    /**
     * 运营员登录
     *
     * @param loginCommand 运营人员登录请求模型
     * @return token
     */
    String login(LoginCommand loginCommand);
}
