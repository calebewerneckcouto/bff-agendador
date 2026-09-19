package com.javanauta.bff_agendador.controller;

import com.javanauta.bff_agendador.business.CronService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cron")
@RequiredArgsConstructor
@Tag(name = "Cron", description = "Execucao manual do cron de notificacao")
public class CronController {

    private final CronService cronService;

    @PostMapping("/executar")
    @Operation(summary = "Executa o cron de notificacao agora")
    public ResponseEntity<String> executar() {
        cronService.buscaTarefasProximaHora();
        return ResponseEntity.ok("Cron executado");
    }
}
