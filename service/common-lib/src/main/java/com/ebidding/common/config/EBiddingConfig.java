package com.ebidding.common.config;

import com.ebidding.common.auth.AuthFeignRequestInterceptor;
import feign.RequestInterceptor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EBiddingConfig {
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RequestInterceptor getRequestInterceptor() {
        return new AuthFeignRequestInterceptor();
    }

}
