package com.javanauta.bff_agendador.infrastructure.client;

import com.javanauta.bff_agendador.business.dto.EnderecoDTO;
import com.javanauta.bff_agendador.business.dto.TelefoneDTO;
import com.javanauta.bff_agendador.business.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "usuario", url = "${usuario.url}", path = "/usuario")
public interface UsuarioClient {

    @PostMapping
    UsuarioDTO salvaUsuario(@RequestBody UsuarioDTO usuarioDTO);

    @PostMapping("/login")
    Map<String, String> login(@RequestBody UsuarioDTO usuarioDTO);

    @GetMapping
    UsuarioDTO buscaUsuarioPorEmail(@RequestParam("email") String email,
                                    @RequestHeader("Authorization") String token);

    @GetMapping("/todos")
    List<UsuarioDTO> buscarTodosUsuarios();

    @DeleteMapping("/{email}")
    void deletaUsuarioPorEmail(@PathVariable("email") String email,
                               @RequestHeader("Authorization") String token);

    @PutMapping
    UsuarioDTO atualizaDadosUsuario(@RequestBody UsuarioDTO dto,
                                    @RequestHeader("Authorization") String token);

    @PostMapping("/endereco")
    EnderecoDTO cadastraEndereco(@RequestBody EnderecoDTO dto);

    @PostMapping("/telefone")
    TelefoneDTO cadastraTelefone(@RequestBody TelefoneDTO dto,
                                 @RequestHeader("Authorization") String token);

    @PutMapping("/endereco")
    EnderecoDTO atualizaEndereco(@RequestBody EnderecoDTO dto,
                                 @RequestParam("id") Long id,
                                 @RequestHeader("Authorization") String token);

    @PutMapping("/telefone")
    TelefoneDTO atualizaTelefone(@RequestBody TelefoneDTO dto,
                                 @RequestParam("id") Long id,
                                 @RequestHeader("Authorization") String token);
}
