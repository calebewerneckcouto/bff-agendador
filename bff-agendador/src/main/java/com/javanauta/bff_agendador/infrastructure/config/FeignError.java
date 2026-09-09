package com.javanauta.bff_agendador.infrastructure.config;

import com.javanauta.bff_agendador.infrastructure.exception.BusinessException;
import com.javanauta.bff_agendador.infrastructure.exception.ConflictException;
import com.javanauta.bff_agendador.infrastructure.exception.IllegalArgumentException;
import com.javanauta.bff_agendador.infrastructure.exception.ResourceNotFoundException;
import com.javanauta.bff_agendador.infrastructure.exception.UnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FeignError implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String mensagemErro = mensagemErro(response);

        if (methodKey.contains("buscarDadosCep")) {
            return new IllegalArgumentException("Cep invalido" + mensagemErro);
        }

        switch (response.status()) {
            case 400:
                return new IllegalArgumentException("Erro atributo invalido" + mensagemErro);
            case 401:
                return new UnauthorizedException("Erro usuario nao autenticado" + mensagemErro);
            case 403:
            case 404:
                return new ResourceNotFoundException("Erro atributo nao encontrado" + mensagemErro);
            case 409:
                return new ConflictException("Erro atributo ja existente" + mensagemErro);
            default:
                return new BusinessException("Erro de servidor" + mensagemErro);
        }
    }

    private String mensagemErro(Response response) {
        try {
            if (Objects.isNull(response.body())) {
                return "";
            }
            String body = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
            return body.isBlank() ? "" : " " + body;
        } catch (IOException e) {
            return "";
        }
    }
}
