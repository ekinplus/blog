package com.ekin.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableCaching
@Configuration
public class MvcConfig implements WebMvcConfigurer {

}
