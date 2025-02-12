package com.ev.realestate.ratelimit;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.time.Duration;

@Configuration
public class RateLimitConfig {

    @Bean
    public RateLimitService rateLimitService() {
       return new RateLimitService(50, Duration.ofMinutes(1));
    }

    @Bean
    public FilterRegistrationBean<RateLimitFilter> rateLimitFilter(
            RateLimitService rateLimitService
    ) {
        FilterRegistrationBean<RateLimitFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RateLimitFilter(rateLimitService));
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registrationBean;
    }
}