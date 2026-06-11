package com.cskaoyan.duolai.clean.common.config;

import com.cskaoyan.duolai.clean.common.handler.UserInfoHandler;
import com.cskaoyan.duolai.clean.common.interceptor.FeignInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@EnableFeignClients(basePackages = "com.cskaoyan.duolai.clean")
@ConditionalOnProperty(prefix = "feign", name = "enable", havingValue = "true")
public class ClientScanConfiguration {

    @Bean
    public FeignInterceptor feignInterceptor(UserInfoHandler userInfoHandler){
        return new FeignInterceptor(userInfoHandler);
    }


}
