package com.javanauta.bff_agendador.infrastructure.client;

import com.javanauta.bff_agendador.business.dto.EnderecoDTO;
import com.javanauta.bff_agendador.business.dto.TarefasDTO;
import com.javanauta.bff_agendador.business.dto.TelefoneDTO;
import com.javanauta.bff_agendador.business.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "notificacao", url = "${notificacao.url}", path = "/email")
public interface EmailClient {

 void enviarEmail(@RequestBody TarefasDTO dto);
}
