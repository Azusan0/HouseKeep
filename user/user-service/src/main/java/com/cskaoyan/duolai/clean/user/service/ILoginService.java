package com.cskaoyan.duolai.clean.user.service;


import com.cskaoyan.duolai.clean.user.request.LoginForCustomerCommand;
import com.cskaoyan.duolai.clean.user.request.LoginForWorkCommand;
import com.cskaoyan.duolai.clean.user.dto.LoginDTO;

/**
 * 客户中心登录业务
 */
public interface ILoginService {

    /**
     * 机构人员账号密码登录
     *
     * @param loginForWorkCommand 登录参数
     * @return
     */
    LoginDTO loginForWorker(LoginForWorkCommand loginForWorkCommand);

    /**
     * 客户人员登录接口
     *
     * @param loginForCustomerCommand 登录请求
     * @return token
     */
    LoginDTO loginForCommonUser(LoginForCustomerCommand loginForCustomerCommand);
}
