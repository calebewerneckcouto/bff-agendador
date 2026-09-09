package com.javanauta.bff_agendador.infrastructure.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignError();
    }
}
