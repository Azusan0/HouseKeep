package com.cskaoyan.duolai.clean.mvc.config;

import com.cskaoyan.duolai.clean.mvc.filter.PackResultFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PackResultFilter.class)
public class FilterConfiguration {
}
