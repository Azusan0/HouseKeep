package com.cskaoyan.duolai.clean.mvc.config;

import com.cskaoyan.duolai.clean.mvc.handler.UserInfoHandlerImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({UserInfoHandlerImpl.class})
public class AutoConfiguration {
}
