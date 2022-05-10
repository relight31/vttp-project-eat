package com.vttp.miniproject.Project;

import com.vttp.miniproject.Project.filters.LoginFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean<LoginFilter> registerFilter() {
        // create an instance of LoginFilter
        LoginFilter filter = new LoginFilter();

        // create an instance of registration bean
        FilterRegistrationBean<LoginFilter> loginFilter = new FilterRegistrationBean<>();
        loginFilter.setFilter(filter);
        loginFilter.addUrlPatterns("/auth/*");

        return loginFilter;
    }
}
