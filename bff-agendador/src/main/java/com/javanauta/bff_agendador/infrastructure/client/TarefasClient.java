package com.javanauta.bff_agendador.infrastructure.client;

import com.javanauta.bff_agendador.business.dto.TarefasDTO;
import com.javanauta.bff_agendador.business.enums.StatusNotificacao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "tarefas", url = "${tarefas.url}", path = "/tarefas")
public interface TarefasClient {

    @PostMapping
    TarefasDTO gravarTarefas(@RequestBody TarefasDTO tarefasDTO,
                             @RequestHeader("Authorization") String token);

    @GetMapping("/eventos")
    List<TarefasDTO> buscaTarefasAgendadasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,
            @RequestHeader("Authorization") String token);

    @GetMapping
    List<TarefasDTO> buscaTarefasPorEmail(@RequestHeader("Authorization") String token);

    @DeleteMapping
    void deletaTarefaPorId(@RequestParam("id") String id,
                           @RequestHeader("Authorization") String token);

    @PatchMapping
    TarefasDTO alteraStatusNotificacao(@RequestParam("status") StatusNotificacao notificacao,
                                       @RequestParam("id") String id,
                                       @RequestHeader("Authorization") String token);

    @PutMapping
    TarefasDTO updateTarefas(@RequestBody TarefasDTO tarefasDTO,
                             @RequestParam("id") String id,
                             @RequestHeader("Authorization") String token);
}
