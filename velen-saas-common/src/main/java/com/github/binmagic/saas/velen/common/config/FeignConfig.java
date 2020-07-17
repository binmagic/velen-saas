package com.github.binmagic.saas.velen.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class FeignConfig {

    @Bean
    public HttpMessageConverters feignHttpMessageConverter() {
        final HttpMessageConverters httpMessageConverters = new HttpMessageConverters();
        return httpMessageConverters;
    }

}
