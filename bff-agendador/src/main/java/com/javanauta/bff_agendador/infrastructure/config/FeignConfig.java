package com.javanauta.bff_agendador.infrastructure.config;

import feign.Client;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;

public class FeignConfig {

    @Bean
    public Client feignClient() {
        return new OkHttpClient();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignError();
    }

    @Bean
    public RequestInterceptor feignAuthInterceptor() {
        return new FeignAuthInterceptor();
    }
}
