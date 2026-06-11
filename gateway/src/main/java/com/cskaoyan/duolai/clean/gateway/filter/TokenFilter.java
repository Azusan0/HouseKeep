package com.cskaoyan.duolai.clean.gateway.filter;


import com.cskaoyan.duolai.clean.common.constants.ErrorInfo;
import com.cskaoyan.duolai.clean.common.constants.HeaderConstants;
import com.cskaoyan.duolai.clean.common.model.CurrentUserInfo;
import com.cskaoyan.duolai.clean.common.utils.Base64Utils;
import com.cskaoyan.duolai.clean.common.utils.JsonUtils;
import com.cskaoyan.duolai.clean.common.utils.JwtTool;
import com.cskaoyan.duolai.clean.common.utils.StringUtils;
import com.cskaoyan.duolai.clean.gateway.properties.ApplicationProperties;
import com.cskaoyan.duolai.clean.gateway.utils.GatewayWebUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static cn.hutool.json.JSONUtil.toJsonStr;


/**
 * token解析过滤器
 */
@Slf4j
@Component
public class TokenFilter implements GlobalFilter {

    /**
     * token header名称
     */
    private static final String HEADER_TOKEN = "Authorization";

    // 注入关于登录白名单
    @Resource
    private ApplicationProperties applicationProperties;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //1. 获取请求的请求路径
        String path = GatewayWebUtils.getUri(exchange);
        //2.当前请求是否需要登录才能访问
        if (applicationProperties.getAccessPathWhiteList().contains(path)) {
            //放行
            chain.filter(exchange);
        }
        //3.验证登录身份

        //3.1从请求头中获得JWT的token
        String jwtTokenStr = GatewayWebUtils.getRequestHeader(exchange, HEADER_TOKEN);
        if (StringUtils.isEmpty(jwtTokenStr)) {
            return GatewayWebUtils.toResponse(exchange, HttpStatus.FORBIDDEN.value(), ErrorInfo.Msg.REQUEST_FORBIDDEN);

        }
        //3.2 先获取用户类型，再根据用户类型获得相应的密钥
        Integer userType = JwtTool.getUserType(jwtTokenStr);
        String tokenSecret = applicationProperties.getTokenKey().get(userType + "");
        if (StringUtils.isEmpty(tokenSecret)) {

        //3.3没有获取到这种用户类型对应的秘钥
            return GatewayWebUtils.toResponse(exchange, HttpStatus.FORBIDDEN.value(), ErrorInfo.Msg.REQUEST_FORBIDDEN);
        }
        JwtTool jwtTool = new JwtTool(tokenSecret);
        CurrentUserInfo userInfo = jwtTool.parseToken(jwtTokenStr);
        if (userInfo == null) {
            //解析token字符串失败
            return GatewayWebUtils.toResponse(exchange, HttpStatus.FORBIDDEN.value(), "登录信息过期或拒绝访问");
        }
        //3.4jwt的token解析成功，将用户的信息保存到自定义的请求头中，转发给目标服务
        //3.4.1将用户信息转化为json
        String userInfoJson=JsonUtils.toJsonStr(userInfo);
        //3.4.2将用户信息json信息转化为base64编码的字符串
        String base64UserInfo = Base64Utils.encodeStr(userInfoJson);
        ServerWebExchange serverWebExchange=GatewayWebUtils.setRequestHeader(exchange, HeaderConstants.USER_INFO,base64UserInfo);

        return chain.filter(serverWebExchange);
    }


}
