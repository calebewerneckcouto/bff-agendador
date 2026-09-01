package com.javanauta.bff_agendador.business;

import com.javanauta.bff_agendador.business.dto.EnderecoDTO;
import com.javanauta.bff_agendador.business.dto.TelefoneDTO;
import com.javanauta.bff_agendador.business.dto.UsuarioDTO;
import com.javanauta.bff_agendador.infrastructure.client.UsuarioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioClient usuarioClient;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        return usuarioClient.salvaUsuario(usuarioDTO);
    }

    public Map<String, String> login(UsuarioDTO usuarioDTO) {
        return usuarioClient.login(usuarioDTO);
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email, String token) {
        return usuarioClient.buscaUsuarioPorEmail(email, token);
    }

    public List<UsuarioDTO> buscarTodosUsuarios() {
        return usuarioClient.buscarTodosUsuarios();
    }

    public void deletaUsuarioPorEmail(String email, String token) {
        usuarioClient.deletaUsuarioPorEmail(email, token);
    }

    public UsuarioDTO atualizaDadosUsuario(UsuarioDTO usuarioDTO, String token) {
        return usuarioClient.atualizaDadosUsuario(usuarioDTO, token);
    }

    public EnderecoDTO cadastraEndereco(EnderecoDTO enderecoDTO) {
        return usuarioClient.cadastraEndereco(enderecoDTO);
    }

    public TelefoneDTO cadastraTelefone(TelefoneDTO telefoneDTO, String token) {
        return usuarioClient.cadastraTelefone(telefoneDTO, token);
    }

    public EnderecoDTO atualizaEndereco(Long id, EnderecoDTO enderecoDTO, String token) {
        return usuarioClient.atualizaEndereco(enderecoDTO, id, token);
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO, String token) {
        return usuarioClient.atualizaTelefone(telefoneDTO, idTelefone, token);
    }
}
