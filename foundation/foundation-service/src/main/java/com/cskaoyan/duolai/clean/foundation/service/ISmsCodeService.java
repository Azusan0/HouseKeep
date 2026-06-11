package com.cskaoyan.duolai.clean.foundation.service;

import com.cskaoyan.duolai.clean.foundation.model.dto.request.SmsCodeCommand;
import com.cskaoyan.duolai.clean.common.enums.SmsBussinessTypeEnum;

public interface ISmsCodeService {

    /**
     * 发送短信验证码
     * @param smsCodeCommand
     */
    String smsCodeSend(SmsCodeCommand smsCodeCommand);

    /**
     * 校验短信验证码
     * @param phone 验证手机号
     * @param bussinessType 业务类型
     * @return 验证结果
     */
    boolean verify(String phone, SmsBussinessTypeEnum bussinessType, String verifyCode);
}
