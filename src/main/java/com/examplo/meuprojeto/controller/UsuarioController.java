package com.examplo.meuprojeto.controller;


import com.examplo.meuprojeto.dto.UsuarioRequestDTO;
import com.examplo.meuprojeto.dto.UsuarioResponseDTO;
import com.examplo.meuprojeto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping()
    public List<UsuarioResponseDTO> listarUsuario() {

        return  usuarioService.listarUsuarios();
    }
   @GetMapping("/{id}")
   public  ResponseEntity<UsuarioResponseDTO> buscarUsuario(@PathVariable Long id) {
       return  usuarioService.buscarUsuario(id).map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
   }

   @PostMapping()
   public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.criarUsuario(usuarioRequestDTO);
        return ResponseEntity.status(201).build();
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
