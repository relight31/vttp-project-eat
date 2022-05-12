package com.vttp.miniproject.Project;

import java.util.logging.Logger;

import com.vttp.miniproject.Project.filters.LoginFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    Logger logger = Logger.getLogger(AppConfig.class.getName());

    @Bean
    public FilterRegistrationBean<LoginFilter> registerFilter() {
        // create an instance of LoginFilter
        LoginFilter filter = new LoginFilter();
        logger.info("LoginFilter instantiated");

        // create an instance of registration bean
        FilterRegistrationBean<LoginFilter> loginFilter = new FilterRegistrationBean<>();
        logger.info("FilterRegistrationBean instantiated");
        loginFilter.setFilter(filter);
        logger.info("filter set");
        loginFilter.addUrlPatterns("/auth/*");
        logger.info("URL pattern set");

        return loginFilter;
    }
}
