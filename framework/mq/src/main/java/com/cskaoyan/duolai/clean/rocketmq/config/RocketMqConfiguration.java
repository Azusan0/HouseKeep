package com.cskaoyan.duolai.clean.rocketmq.config;

import com.cskaoyan.duolai.clean.rocketmq.properties.RocketMqProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.Resource;

@Configuration
public class RocketMqConfiguration {

    @Resource
    RocketMqProperties mqProperties;
}
