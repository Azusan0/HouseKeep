package com.cskaoyan.duolai.clean.foundation.service.impl;

import com.cskaoyan.duolai.clean.foundation.model.dto.request.SmsCodeCommand;
import com.cskaoyan.duolai.clean.foundation.service.ISmsCodeService;
import com.cskaoyan.duolai.clean.common.constants.CommonRedisConstants;
import com.cskaoyan.duolai.clean.common.enums.SmsBussinessTypeEnum;
import com.cskaoyan.duolai.clean.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SmsCodeServiceImpl implements ISmsCodeService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String smsCodeSend(SmsCodeCommand smsCodeCommand) {
        if(StringUtils.isEmpty(smsCodeCommand.getPhone()) || StringUtils.isEmpty(smsCodeCommand.getBussinessType())) {
            log.debug("不能发送短信验证码，phone:{},bussinessType:{}", smsCodeCommand.getPhone(), smsCodeCommand.getBussinessType());
            return null;
        }
        String redisKey = String.format(CommonRedisConstants.RedisKey.VERIFY_CODE, smsCodeCommand.getPhone(), smsCodeCommand.getBussinessType());
        // 取6位随机数
        String verifyCode = (int)(Math.random() * 1000000) + "";
        //String verifyCode = "123456";//为方便测试固定为123456
        log.info("向手机号{}发送验证码{}", smsCodeCommand.getPhone(),verifyCode);
        //todo调用短信平台接口向指定手机发验证码...
        // 短信验证码有效期5分钟
        redisTemplate.opsForValue().set(redisKey, verifyCode, 300, TimeUnit.SECONDS);
        return verifyCode;
    }

    @Override
    public boolean verify(String phone, SmsBussinessTypeEnum bussinessType, String verifyCode) {
        // 1.验证前准备
        String redisKey = String.format(CommonRedisConstants.RedisKey.VERIFY_CODE, phone, bussinessType.getType());
        String verifyCodeInRedis = redisTemplate.opsForValue().get(redisKey);

        // 2.短验验证，验证通过后删除code，code只能使用一次
        boolean verifyResult = StringUtils.isNotEmpty(verifyCode) && verifyCode.equals(verifyCodeInRedis);
        if(verifyResult) {
            redisTemplate.delete(redisKey);
        }
        return verifyResult;
    }
}
