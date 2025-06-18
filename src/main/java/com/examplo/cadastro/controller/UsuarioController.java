package com.examplo.cadastro.controller;

import com.examplo.cadastro.dto.UsuarioRequestDTO;
import com.examplo.cadastro.dto.UsuarioResponseDTO;
import com.examplo.cadastro.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuario", description = "Cadastro de membros da igreja Batista Aliança")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(
            summary = "Lista todos os usuários cadastrados no sistema.",
            description = "Retorna todos os usuários cadastrados no formato UsuarioResponseDTO, com dados públicos e organizados."
    )

    @GetMapping()
    public List<UsuarioResponseDTO> listarUsuarios() {
        return  usuarioService.listarUsuarios();
    }

    @Operation(
            summary ="Retorna um usuário específico com base no ID informado.",
            description ="Este endpoint realiza a busca de um usuário específico com base no seu id. Ele retorna um objeto UsuarioResponseDTO, encapsulado dentro de um Optional, com status HTTP 200 (OK)"
    )

   @GetMapping("/{id}")
   public  ResponseEntity<Optional<UsuarioResponseDTO>> buscarUsuario(@PathVariable Long id) {
       return ResponseEntity.status(HttpStatus.OK).body(usuarioService.buscarUsuario(id));
   }

    @Operation(
            summary ="Cria um novo usuário com os dados fornecidos.",
            description = "Este endpoint recebe um objeto UsuarioRequestDTO no corpo da requisição (@RequestBody) com os dados do novo usuário. Ele chama o serviço responsável pela criação e retorna o UsuarioResponseDTO correspondente"
    )
    @PostMapping()
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody UsuarioRequestDTO usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.criarUsuario(usuario));
    }

    @Operation(
            summary ="Atualiza os dados de um usuário existente com base no ID informado.",
            description = "Este endpoint permite atualizar um usuário específico. Recebe o id do usuário via @PathVariable e os novos dados através do corpo da requisição (@RequestBody com UsuarioRequestDTO)."
    )

   @PutMapping("/{id}")
   public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        return usuarioService.atualizarUsuario(id,usuarioRequestDTO).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
   }

    @Operation(
            summary = "Remove um usuário existente com base no ID fornecido.",
            description ="Este endpoint exclui o usuário correspondente ao id informado na URL. Ele chama o serviço responsável pela exclusão (usuarioService.deletarUsuario)"
    )

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
         usuarioService.deletarUsuario(id);
         return ResponseEntity.noContent().build();
   }
   
}
