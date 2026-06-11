package com.cskaoyan.duolai.clean.housekeeping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = "com.cskaoyan.duolai.clean")
@MapperScan("com.cskaoyan.duolai.clean.housekeeping.dao.mapper")
@EnableAspectJAutoProxy
@EnableCaching
@EnableFeignClients
public class HouseKeepingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseKeepingServiceApplication.class, args);
    }
}
