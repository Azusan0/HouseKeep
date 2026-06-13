package com.cskaoyan.duolai.clean.user.config;

import com.cskaoyan.duolai.clean.user.properties.ApplicationProperties;
import com.cskaoyan.duolai.clean.common.utils.JwtTool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class JwtConfiguration {

    @Resource
    private ApplicationProperties applicationProperties;

    @Bean
    public JwtTool jwtTool() {
        return new JwtTool(applicationProperties.getJwtKey());
    }
}
