package com.cskaoyan.duolai.clean.common.interceptor;

import com.cskaoyan.duolai.clean.common.model.CurrentUserInfo;
import com.cskaoyan.duolai.clean.common.handler.UserInfoHandler;
import com.cskaoyan.duolai.clean.common.utils.Base64Utils;
import com.cskaoyan.duolai.clean.common.utils.JsonUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

/**
 * 向下一个服务传递参数，用户信息、访问来源、访问id
 */import static com.cskaoyan.duolai.clean.common.constants.HeaderConstants.*;

@Slf4j
@ConditionalOnBean(value = {UserInfoHandler.class})
public class FeignInterceptor implements RequestInterceptor {
    private final UserInfoHandler userInfoHandler;

    public FeignInterceptor(UserInfoHandler userInfoHandler) {
        this.userInfoHandler = userInfoHandler;
    }

    /**
     * 将用户信息base64格式编码，传递到下一个微服务
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
//         1.用户信息
        CurrentUserInfo userInfo = userInfoHandler.currentUserInfo();
        String userInfoStr = Base64Utils.encodeStr(JsonUtils.toJsonStr(userInfo));
        requestTemplate.header(USER_INFO, userInfoStr);

    }
}
