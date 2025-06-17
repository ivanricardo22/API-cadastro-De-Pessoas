package com.examplo.meuprojeto.service;

import com.examplo.meuprojeto.dto.UsuarioRequestDTO;
import com.examplo.meuprojeto.dto.UsuarioResponseDTO;
import com.examplo.meuprojeto.exception.BadRequestException;
import com.examplo.meuprojeto.model.Usuario;
import com.examplo.meuprojeto.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(this::convertToResponseDTO).toList();

    }

    @Override
    public Optional<UsuarioResponseDTO> buscarUsuario(Long id) {
        Usuario u = buscarOuLancar(id);
        return Optional.of(convertToResponseDTO(u));
    }

    @Override
    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        validacaoCampoUsuario(usuarioRequestDTO);
        validacaoNomeExistente(usuarioRequestDTO);
        Usuario usuario = convertToEntity(usuarioRequestDTO);
        usuarioRepository.save(usuario);
        return convertToResponseDTO(usuario);
    }

    @Override
    public Optional<UsuarioResponseDTO> atualizarUsuario(Long id, UsuarioRequestDTO usuarioRequestDTO) {
        Usuario u = buscarOuLancar(id);
        validacaoNomeExistente(usuarioRequestDTO);

        u.setName(usuarioRequestDTO.getName());
        u.setBirthData(usuarioRequestDTO.getBirthData());
        u.setTelephone(usuarioRequestDTO.getTelephone());
        u.setAddress(usuarioRequestDTO.getAddress());

        return Optional.of(convertToResponseDTO(usuarioRepository.save(u)));

    }

    @Override
    public void deletarUsuario(Long id) {
        buscarOuLancar(id);
        usuarioRepository.deleteById(id);
    }

    private UsuarioResponseDTO convertToResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setName(usuario.getName());
        dto.setBirthData(usuario.getBirthData());
        dto.setTelephone(usuario.getTelephone());
        dto.setAddress(usuario.getAddress());
        return dto;
    }

    private Usuario convertToEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setName(dto.getName());
        usuario.setBirthData(dto.getBirthData());
        usuario.setTelephone(dto.getTelephone());
        usuario.setAddress(dto.getAddress());
        return usuario;
    }

    private void validacaoCampoUsuario(UsuarioRequestDTO r) {
        if (r.getName() == null || r.getName().isEmpty()) {
            throw new BadRequestException("O campo 'name' é obrigatório.");
        } else if (r.getBirthData() == null) {
            throw new BadRequestException("O campo BirthData é obrigatório.");
        } else if (r.getTelephone() == null || r.getTelephone().isEmpty()) {
            throw new BadRequestException("O campo telephone é obrigatório ");
        } else if (r.getAddress() == null || r.getAddress().isEmpty()) {
            throw new BadRequestException("O campo address é obrigatório ");
        }
    }

    private void validacaoNomeExistente(UsuarioRequestDTO u) {
        if (usuarioRepository.existsByName(u.getName())) {
            throw new BadRequestException(" Nome já cadastrado. ");
        }
    }

    private Usuario buscarOuLancar(Long id) {
         return usuarioRepository.findById(id).orElseThrow(() -> new BadRequestException("id não encontrado"));
    }

}
