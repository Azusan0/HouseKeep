package com.cskaoyan.duolai.clean.user.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "duolai.clean")
@Configuration
@Data
public class ApplicationProperties {

    private String jwtKey;
}
