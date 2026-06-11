package com.cskaoyan.duolai.clean.housekeeping.config;

import com.cskaoyan.duolai.clean.common.utils.JwtTool;
import com.cskaoyan.duolai.clean.housekeeping.properties.ApplicaitonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.Resource;

@Configuration
public class JwtConfiguration {

    @Resource
    private ApplicaitonProperties applicaitonProperties;

    @Bean
    public JwtTool jwtTool() {
        return new JwtTool(applicaitonProperties.getJwtKey());
    }
}
