package com.cskaoyan.duolai.clean.housekeeping.service.impl;

import com.cskaoyan.duolai.clean.common.constants.UserType;
import com.cskaoyan.duolai.clean.common.expcetions.ForbiddenOperationException;
import com.cskaoyan.duolai.clean.common.utils.JwtTool;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.OperatorDO;
import com.cskaoyan.duolai.clean.housekeeping.request.LoginCommand;
import com.cskaoyan.duolai.clean.housekeeping.service.ILoginService;
import com.cskaoyan.duolai.clean.housekeeping.service.IOperatorService;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements ILoginService {

    @Resource
    private IOperatorService operatorService;
    @Resource
    private JwtTool jwtTool;
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 运营员登录
     *
     * @param loginCommand 运营人员登录请求模型
     * @return token
     */
    @Override
    public String login(LoginCommand loginCommand) {

        //1.根据用户名查询用户信息
        OperatorDO operatorDO = operatorService.findByUsername(loginCommand.getUsername());
        if (operatorDO == null) {
            throw new ForbiddenOperationException("当前用户不存在");
        }
        //2.匹配用户输入的密码
        boolean matches=passwordEncoder.matches(loginCommand.getPassword(),operatorDO.getPassword());
        if (!matches) {
            throw new ForbiddenOperationException("当前用户密码不正确");

        }
        //3.生成用户JWT Token字符串 UserType.OPERATION为后台用户类型
        String token=jwtTool.createToken(operatorDO.getId(),operatorDO.getName(),operatorDO.getAvatar(), UserType.OPERATION);
        return null;
    }
}
