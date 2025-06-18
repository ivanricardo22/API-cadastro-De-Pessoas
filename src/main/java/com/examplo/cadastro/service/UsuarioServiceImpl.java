package com.examplo.cadastro.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.examplo.cadastro.dto.UsuarioRequestDTO;
import com.examplo.cadastro.dto.UsuarioResponseDTO;
import com.examplo.cadastro.exception.BadRequestException;
import com.examplo.cadastro.model.Usuario;
import com.examplo.cadastro.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Tag(name = "Serviço de Membros",
        description = "Implementação dos serviços de gerenciamento de membros da igreja")
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Operation(summary = "Listar todos os membros",
            description = "Retorna uma lista com todos os membros cadastrados na igreja")
    @ApiResponse(responseCode = "200", description = "Lista de membros retornada com sucesso")
    @Override
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(this::convertToResponseDTO).toList();
    }

    @Operation(summary = "Buscar membro por ID",
            description = "Busca um membro específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Membro encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID não encontrado")
    })
    @Override
    public Optional<UsuarioResponseDTO> buscarUsuario(Long id) {
        Usuario u = buscarOuLancar(id);
        return Optional.of(convertToResponseDTO(u));
    }

    @Operation(summary = "Criar novo membro",
            description = "Cadastra um novo membro na igreja")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Membro cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou nome já cadastrado")
    })
    @Override
    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        validacaoCampoUsuario(usuarioRequestDTO);
        validacaoNomeExistente(usuarioRequestDTO);
        Usuario usuario = convertToEntity(usuarioRequestDTO);
        usuarioRepository.save(usuario);
        return convertToResponseDTO(usuario);
    }

    @Operation(summary = "Atualizar membro",
            description = "Atualiza os dados de um membro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do membro atualizados com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID não encontrado ou nome já cadastrado")
    })
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

    @Operation(summary = "Deletar membro",
            description = "Remove um membro do cadastro da igreja")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Membro removido com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID não encontrado")
    })
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