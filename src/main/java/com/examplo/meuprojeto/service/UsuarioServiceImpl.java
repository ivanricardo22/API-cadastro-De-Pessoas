package com.examplo.meuprojeto.service;

import com.examplo.meuprojeto.dto.UsuarioRequestDTO;
import com.examplo.meuprojeto.dto.UsuarioResponseDTO;
import com.examplo.meuprojeto.entity.Usuario;
import com.examplo.meuprojeto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Component
public class UsuarioServiceImpl implements UsuarioServece {


    @Autowired private UsuarioRepository usuarioRepository;


    @Override
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(this::convertToResponseDTO).collect(Collectors.toList());

    }

    @Override
    public Optional<UsuarioResponseDTO> buscarUsuario(Long id) {
        return usuarioRepository.findById(id).map(this::convertToResponseDTO);
    }

    @Override
    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuario = convertToEntity(usuarioRequestDTO);
        return convertToResponseDTO(usuarioRepository.save(usuario));
    }

    @Override
    public Optional<UsuarioResponseDTO> atualizarUsuario(Long id, UsuarioRequestDTO usuarioRequestDTO) {
        return usuarioRepository.findById(id)
                .map(u -> {
                u.setNome(usuarioRequestDTO.getNome());
                u.setEmail(usuarioRequestDTO.getEmail());
                u.setIdade(usuarioRequestDTO.getIdade());

                return convertToResponseDTO(usuarioRepository.save(u));

                });

    }

    @Override
    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    private UsuarioResponseDTO convertToResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setIdade(usuario.getIdade());
        return dto; }

    private Usuario convertToEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setIdade(dto.getIdade());
        return usuario;
    }
}
