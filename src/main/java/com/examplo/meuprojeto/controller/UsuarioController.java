package com.examplo.meuprojeto.controller;


import com.examplo.meuprojeto.dto.UsuarioRequestDTO;
import com.examplo.meuprojeto.dto.UsuarioResponseDTO;
import com.examplo.meuprojeto.service.UsuarioServece;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioServece usuarioServece;

    @GetMapping("/listar")
    public List<UsuarioResponseDTO> listarUsuario() {

        return  usuarioServece.listarUsuarios();
    }
   @GetMapping("/{id}")
   public  ResponseEntity<UsuarioResponseDTO> buscarUsuario(@PathVariable Long id) {
       return  usuarioServece.buscarUsuario(id).map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
   }

   @PostMapping("/criar")
   public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO usuarioResponseDTO = usuarioServece.criarUsuario(usuarioRequestDTO);
        return ResponseEntity.status(201).build();
   }

   @PutMapping("/{id}")
   public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long id, UsuarioRequestDTO usuarioRequestDTO) {
        return usuarioServece.atualizarUsuario(id,usuarioRequestDTO).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
   }
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
         usuarioServece.deletarUsuario(id);
         return ResponseEntity.noContent().build();
   }



}
