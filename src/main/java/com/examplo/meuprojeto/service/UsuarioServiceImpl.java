package com.examplo.meuprojeto.service;

import com.examplo.meuprojeto.dto.UsuarioRequestDTO;
import com.examplo.meuprojeto.dto.UsuarioResponseDTO;
import com.examplo.meuprojeto.entity.Usuario;
import com.examplo.meuprojeto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {


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
                u.setDataDeAniversario(usuarioRequestDTO.getDataDeAniversario());
                u.setTelefone(usuarioRequestDTO.getTelefone());
                u.setEndereco(usuarioRequestDTO.getEndereco());

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
        dto.setDataDeAniversario(usuario.getDataDeAniversario());
        dto.setTelefone(usuario.getTelefone());
        dto.setEndereco(usuario.getEndereco());
        return dto; }

    private Usuario convertToEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setDataDeAniversario(dto.getDataDeAniversario());
        usuario.setTelefone(dto.getTelefone());
        usuario.setEndereco(dto.getEndereco());
        return usuario;
    }
}
