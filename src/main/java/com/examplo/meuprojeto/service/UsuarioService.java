package com.examplo.meuprojeto.service;

import com.examplo.meuprojeto.dto.UsuarioRequestDTO;
import com.examplo.meuprojeto.dto.UsuarioResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface UsuarioService {

    List<UsuarioResponseDTO> listarUsuarios();
    Optional<UsuarioResponseDTO>buscarUsuario(Long id);
    UsuarioResponseDTO criarUsuario(UsuarioRequestDTO usuarioRequestDTO);
    Optional<UsuarioResponseDTO> atualizarUsuario(Long id, UsuarioRequestDTO usuarioRequestDTO);
    void deletarUsuario(Long id);

}
