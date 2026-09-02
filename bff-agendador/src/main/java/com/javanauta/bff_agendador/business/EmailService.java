package com.javanauta.bff_agendador.business;

import com.javanauta.bff_agendador.business.dto.TarefasDTO;
import com.javanauta.bff_agendador.infrastructure.client.EmailClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailClient emailClient;

    public void enviaEmail(TarefasDTO dto) {
        emailClient.enviarEmail(dto);
    }
}
