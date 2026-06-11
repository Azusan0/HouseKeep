package com.cskaoyan.duolai.clean.rocketmq.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "rocketmq.producer")
@Configuration
public class RocketMqProperties {

    private String group;
}
