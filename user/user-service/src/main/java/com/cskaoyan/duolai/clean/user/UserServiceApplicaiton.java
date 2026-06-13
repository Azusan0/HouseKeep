package com.cskaoyan.duolai.clean.user;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = "com.cskaoyan.duolai.clean")
@Slf4j
@EnableCaching
@MapperScan("com.cskaoyan.duolai.clean.user.dao.mapper")
@EnableFeignClients
public class UserServiceApplicaiton {

    public static void main(String[] args) {
        new SpringApplicationBuilder(UserServiceApplicaiton.class)
                .build(args)
                .run(args);
        log.info("家政服务-客户中心启动");
    }
}
