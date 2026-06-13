package com.cskaoyan.duolai.clean.user.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.cskaoyan.duolai.clean.dto.OpenIdResDTO;
import com.cskaoyan.duolai.clean.user.client.SmsCodeApi;
import com.cskaoyan.duolai.clean.user.client.WechatApi;
import com.cskaoyan.duolai.clean.user.converter.CommonUserConverter;
import com.cskaoyan.duolai.clean.user.dao.entity.CommonUserDO;
import com.cskaoyan.duolai.clean.user.request.LoginForCustomerCommand;
import com.cskaoyan.duolai.clean.user.request.LoginForWorkCommand;
import com.cskaoyan.duolai.clean.user.dto.LoginDTO;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderDTO;
import com.cskaoyan.duolai.clean.user.service.ICommonUserService;
import com.cskaoyan.duolai.clean.user.service.ILoginService;
import com.cskaoyan.duolai.clean.user.service.IServeProviderService;
import com.cskaoyan.duolai.clean.common.constants.CommonStatusConstants;
import com.cskaoyan.duolai.clean.common.constants.ErrorInfo;
import com.cskaoyan.duolai.clean.common.constants.UserType;
import com.cskaoyan.duolai.clean.common.enums.SmsBussinessTypeEnum;
import com.cskaoyan.duolai.clean.common.expcetions.BadRequestException;
import com.cskaoyan.duolai.clean.common.expcetions.CommonException;
import com.cskaoyan.duolai.clean.common.utils.JwtTool;
import com.cskaoyan.duolai.clean.common.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ILoginServiceImpl implements ILoginService {

    @Resource
    private ICommonUserService commonUserService;

    @Resource
    private JwtTool jwtTool;

    @Resource
    private IServeProviderService serveProviderService;
    @Resource
    private WechatApi wechatApi;
    @Resource
    private SmsCodeApi smsCodeApi;

    @Resource
    CommonUserConverter commonUserConverter;

    @Override
    public LoginDTO loginForWorker(LoginForWorkCommand loginForWorkCommand) {

        // 1.数据校验，验证码为空，则抛出异常 throw new BadequestException("验证码错误，请重新获取");

        // 2. 服务调用foundation服务校验验证码是否正确，如果校验不成功则抛出异常： throw new BadRequestException("验证码错误，请重新获取");
        // SmsCodeApi.verify() 第二个参数值固定为: SmsBussinessTypeEnum.SERVE_STAFF_LOGIN

        // 3. 登录校验，根据手机号获取服务人员信息(调用serveProviderService.findByPhone方法)

        // 4. 如果根据手机号未查询出信息，说明是首次登录，需要保存其信息，调用serveProviderService.addServeProvider方法

        // 5.生成登录token，用户类型为UserType.WORKER
        return null;
    }

    @Override
    public LoginDTO loginForCommonUser(LoginForCustomerCommand loginForCustomerCommand) {
        /*
            调用foundation服务wechatApi.getOpenId,  code换openId,
            如果未获取到(返回的对象为null，或者openId为null)则抛出异常throw new CommonException(ErrorInfo.Code.LOGIN_TIMEOUT, ErrorInfo.Msg.REQUEST_FAILD);
         */


        /*
            根据openId查询用户信息commonUserService.findByOpenId，如果未从数据库查到，需要新增数据如下：
            1. loginForCustomerCommand ——> commonUserDO
            2. 设置openId
            3. 设置nickName: "普通用户"+ RandomUtil.randomInt(10000,99999)
            4. 保存
         */

        //根据用户信息构建token，userType为UserType.C_USER

        return null;
    }
}
