package com.cskaoyan.duolai.clean.foundation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 通用服务启动类
 **/
@Slf4j
@SpringBootApplication(scanBasePackages = "com.cskaoyan.duolai.clean")
public class FoundationApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(FoundationApplication.class)
                .build(args)
                .run(args);
        log.info("家政服务-通用服务启动");
    }
}
