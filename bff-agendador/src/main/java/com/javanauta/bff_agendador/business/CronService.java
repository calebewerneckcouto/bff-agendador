package com.javanauta.bff_agendador.business;

import com.javanauta.bff_agendador.business.dto.TarefasDTO;
import com.javanauta.bff_agendador.business.dto.UsuarioDTO;
import com.javanauta.bff_agendador.business.enums.StatusNotificacao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CronService {

    private final TarefasService tarefasService;
    private final EmailService emailService;
    private final UsuarioService usuarioService;

    @Value("${usuario.email}")
    private String email;

    @Value("${usuario.senha}")
    private String senha;

    @Scheduled(cron = "${cron.tarefas:0 0/5 * * * *}")
    public void buscaTarefasProximaHora() {
        try {
            String token = obterToken();
            LocalDateTime horaFutura = LocalDateTime.now().plusHours(1);
            LocalDateTime horaFuturaMaisCinco = horaFutura.plusMinutes(5);

            List<TarefasDTO> listaTarefas = tarefasService.buscaTarefasAgendadasPorPeriodo(
                    horaFutura, horaFuturaMaisCinco, token);

            log.info("Cron executado. Tarefas pendentes na janela: {}", listaTarefas.size());

            listaTarefas.stream()
                    .filter(tarefa -> StatusNotificacao.PENDENTE.equals(tarefa.getStatusNotificacao()))
                    .forEach(tarefa -> {
                        log.info("Enviando email da tarefa {} para {}", tarefa.getId(), tarefa.getEmailUsuario());
                        emailService.enviaEmail(tarefa);
                        tarefasService.alteraStatus(StatusNotificacao.NOTIFICADO, tarefa.getId(), token);
                        log.info("Tarefa {} marcada como NOTIFICADO", tarefa.getId());
                    });
        } catch (Exception e) {
            log.error("Erro ao executar cron de notificacao de tarefas", e);
        }
    }

    private String obterToken() {
        Map<String, String> loginResponse = usuarioService.login(criarUsuarioLogin());
        String authorization = loginResponse.get("authorization");
        if (authorization != null && !authorization.isBlank()) {
            return authorization;
        }
        return "Bearer " + loginResponse.get("token");
    }

    private UsuarioDTO criarUsuarioLogin() {
        return UsuarioDTO.builder()
                .email(email)
                .senha(senha)
                .build();
    }
}
