package com.examplo.meuprojeto.controller;

import com.examplo.meuprojeto.dto.UsuarioRequestDTO;
import com.examplo.meuprojeto.dto.UsuarioResponseDTO;
import com.examplo.meuprojeto.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuario", description = "Cadastro de membros  da igreja Batista Aliança")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(
            summary = " Lista todos os usuários cadastrados no sistema.",
            description = "Retorna todos os usuários cadastrados no formato UsuarioResponseDTO, com dados públicos e organizados."
    )

    @GetMapping()
    public List<UsuarioResponseDTO> listarUsuarios() {
        return  usuarioService.listarUsuarios();
    }
   @GetMapping("/{id}")
   public  ResponseEntity<Optional<UsuarioResponseDTO>> buscarUsuario(@PathVariable Long id) {
       return ResponseEntity.status(HttpStatus.OK).body(usuarioService.buscarUsuario(id));
   }

    @PostMapping()
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody UsuarioRequestDTO usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.criarUsuario(usuario));
    }

   @PutMapping("/{id}")
   public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        return usuarioService.atualizarUsuario(id,usuarioRequestDTO).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
   }
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
         usuarioService.deletarUsuario(id);
         return ResponseEntity.noContent().build();
   }
}
