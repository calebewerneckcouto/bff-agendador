package com.javanauta.bff_agendador.controller;

import com.javanauta.bff_agendador.business.TarefasService;
import com.javanauta.bff_agendador.business.dto.TarefasDTO;
import com.javanauta.bff_agendador.business.enums.StatusNotificacao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
@Tag(name = "Tarefas", description = "Endpoints de agendamento e consulta de tarefas")
public class TarefasController {

    private final TarefasService tarefasService;

    @PostMapping
    @Operation(summary = "Gravar tarefa")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<TarefasDTO> gravarTarefas(@RequestBody TarefasDTO tarefasDTO,
                                                    @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tarefasService.gravarTarefa(token, tarefasDTO));
    }

    @GetMapping("/eventos")
    @Operation(summary = "Buscar tarefas por periodo")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<TarefasDTO>> buscaListaDeTafefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tarefasService.buscaTarefasAgendadasPorPeriodo(dataInicial, dataFinal, token));
    }

    @GetMapping
    @Operation(summary = "Buscar tarefas por email do usuario logado")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<TarefasDTO>> buscaTarefasPorEmail(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tarefasService.buscaTarefasPorEmail(token));
    }

    @DeleteMapping
    @Operation(summary = "Deletar tarefa por id")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam("id") String id,
                                                  @RequestHeader("Authorization") String token) {
        tarefasService.deletaTarefasPorId(id, token);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @Operation(summary = "Alterar status de notificacao da tarefa")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<TarefasDTO> alteraStatusNotificacao(@RequestParam("status") StatusNotificacao notificacao,
                                                              @RequestParam("id") String id,
                                                              @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tarefasService.alteraStatus(notificacao, id, token));
    }

    @PutMapping
    @Operation(summary = "Atualizar tarefa")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<TarefasDTO> updateTarefas(@RequestBody TarefasDTO tarefasDTO,
                                                    @RequestParam("id") String id,
                                                    @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tarefasService.updateTarefas(tarefasDTO, id, token));
    }
}
