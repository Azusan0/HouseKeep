package com.cskaoyan.duolai.clean.mvc.config;


import com.cskaoyan.duolai.clean.mvc.advice.CommonExceptionHandler;
import com.cskaoyan.duolai.clean.mvc.interceptor.UserContextInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // token拦截器
        registry.addInterceptor(new UserContextInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public CommonExceptionHandler commonExceptionAdvice() {
        return new CommonExceptionHandler();
    }

}
