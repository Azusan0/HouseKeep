package com.cskaoyan.duolai.clean.thirdparty.tencent;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cskaoyan.duolai.clean.thirdparty.core.wechat.WechatService;
import com.cskaoyan.duolai.clean.thirdparty.tencent.properties.WechatProperties;
import com.cskaoyan.duolai.clean.common.expcetions.ServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@ConditionalOnBean(WechatProperties.class)
public class WechatServiceImpl implements WechatService {
    @Autowired
    private WechatProperties wechatProperties;

    // 登录
    private static final String REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code";

    // 获取token
    private static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";

    // 获取手机号
    private static final String PHONE_REQUEST_URL = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=";

    /**
     * 获取openid
     *
     * @param code 登录凭证
     * @return 唯一标识
     */
    @Override
    public String getOpenid(String code) {
        Map<String, Object> requestUrlParam = getAppConfig();
        // 小程序端授权后的code 登录临时凭证
        requestUrlParam.put("js_code", code);
        // 发送post请求读取调用微信接口获取openid用户唯一标识
        String result = HttpUtil.get(REQUEST_URL, requestUrlParam);
        log.info("getOpenid result:{}", result);
        // {"session_key":"QbEw1Bp2OpkeCQ36gXvPRg==","openid":"oV4KY1Exd7NebGjfbYK7_KTPeNm4"}
        JSONObject jsonObject = JSONUtil.parseObj(result);
        if (ObjectUtil.isNotEmpty(jsonObject.getInt("errcode"))) {
            throw new ServerErrorException(jsonObject.getStr("errmsg"));
        }
        return jsonObject.getStr("openid");
    }


    /**
     * 获取应用配置
     *
     * @return 参数集合
     */
    private Map<String, Object> getAppConfig() {
        Map<String, Object> requestUrlParam = new HashMap<>();
        // 小程序appId，开发者后台获取
        requestUrlParam.put("appid", wechatProperties.getAppId());
        // 小程序secret，开发者后台获取
        requestUrlParam.put("secret", wechatProperties.getSecret());
        return requestUrlParam;
    }

}
