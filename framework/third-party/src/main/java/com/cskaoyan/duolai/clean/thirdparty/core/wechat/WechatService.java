package com.cskaoyan.duolai.clean.thirdparty.core.wechat;


public interface WechatService {
    /**
     * 获取openid
     *
     * @param code 登录凭证
     * @return 唯一标识
     */
    String getOpenid(String code);

}
