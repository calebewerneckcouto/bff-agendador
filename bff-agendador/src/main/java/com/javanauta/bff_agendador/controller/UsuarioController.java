package com.javanauta.bff_agendador.controller;


import com.javanauta.bff_agendador.business.UsuarioService;
import com.javanauta.bff_agendador.business.dto.EnderecoDTO;
import com.javanauta.bff_agendador.business.dto.TelefoneDTO;
import com.javanauta.bff_agendador.business.dto.UsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuario", description = "Endpoints de cadastro, login e consulta de usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;


    @PostMapping
    @Operation(summary = "Cadastrar usuario", description = "Cria um novo usuario no sistema")
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login",
            description = "Retorna o token JWT. Copie o campo 'token' e cole no botao Authorize do Swagger."
    )
    public ResponseEntity<Map<String, String>> login(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.login(usuarioDTO));
    }

    @GetMapping
    @Operation(summary = "Buscar usuario por email")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UsuarioDTO> buscaUsuarioPorEmail(@RequestParam("email") String email,
                                                           @RequestHeader("Authorization")String token) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email,token));
    }

    @GetMapping("/todos")
    @Operation(summary = "Buscar todos os usuarios")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<UsuarioDTO>> buscarTodosUsuarios() {
        return ResponseEntity.ok(usuarioService.buscarTodosUsuarios());
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Deletar usuario por email")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable("email") String email,
                                                      @RequestHeader("Authorization")String token) {
        usuarioService.deletaUsuarioPorEmail(email,token);
        return ResponseEntity.ok().build();
    }


    @PutMapping
    @Operation(summary = "Atualizar usuario")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UsuarioDTO> atualizaDadoUsuario(@RequestBody UsuarioDTO dto,
                                                          @RequestHeader("Authorization")String token) {
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(dto,token));
    }

    @PostMapping("/endereco")
    @Operation(summary = "Cadastrar endereco")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<EnderecoDTO> cadastraEndereco(@RequestBody EnderecoDTO dto) {
        return ResponseEntity.ok(usuarioService.cadastraEndereco(dto));
    }

    @PostMapping("/telefone")
    @Operation(summary = "Cadastrar telefone")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<TelefoneDTO> cadastraTelefone(@RequestBody TelefoneDTO dto,
                                                        @RequestHeader("Authorization")String token) {
        return ResponseEntity.ok(usuarioService.cadastraTelefone(dto,token));
    }

    @PutMapping("/endereco")
    @Operation(summary = "Atualizar endereco")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<EnderecoDTO>atualizaEndereco(@RequestBody EnderecoDTO dto,
                                                       @RequestParam("id")Long id,
                                                       @RequestHeader("Authorization")String token){
        return ResponseEntity.ok(usuarioService.atualizaEndereco(id,dto,token));
    }


    @PutMapping("/telefone")
    @Operation(summary = "Atualizar telefone")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<TelefoneDTO>atualizaTelefone(@RequestBody TelefoneDTO dto,
                                                       @RequestParam("id")Long id,
                                                       @RequestHeader("Authorization")String token){
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id,dto,token));
    }

    
}
