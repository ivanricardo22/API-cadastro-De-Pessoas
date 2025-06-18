package com.examplo.meuprojeto;

import com.examplo.meuprojeto.dto.UsuarioRequestDTO;
import com.examplo.meuprojeto.dto.UsuarioResponseDTO;
import com.examplo.meuprojeto.exception.BadRequestException;
import com.examplo.meuprojeto.model.Usuario;
import com.examplo.meuprojeto.repository.UsuarioRepository;
import com.examplo.meuprojeto.service.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarUsuariosComSucesso() {

        LocalDate localDate = LocalDate.of(1999, 2, 25);

        Usuario usuario1 = new Usuario(1L, "Ivan", localDate, "(81)9 99109-9496", "Rua Arroz");



        List<Usuario> usuarios = List.of(usuario1);

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioResponseDTO> resultado = usuarioService.listarUsuarios();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        String nome = resultado.stream()
                .filter(u -> u.getId() == 1L)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Usuário com ID 1 não encontrado"))
                .getName();

        assertEquals("Ivan", nome);


        LocalDate data = resultado.stream()
                .filter(u -> u.getId() == 1L)
                .findFirst()
                .orElseThrow(() -> new BadRequestException("id não encontrado"))
                .getBirthData();

       assertEquals(localDate, data);


        String telefone  = resultado.stream()
                .filter(u -> u.getId() == 1L)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Usuário com ID 1 não encontrado"))
                .getTelephone();

        assertEquals("(81)9 99109-9496", telefone);

    }

    @Test
    void listarUsuariosVazio() {

        List<Usuario> usuarios = new ArrayList<>();

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioResponseDTO> resultado = usuarioService.listarUsuarios();

        assertNotNull(resultado);
        assertEquals(0, resultado.size());

    }

    @Test
    void buscarUsuarioComSucesso() {

        Usuario usuario = new Usuario();
        Usuario usuario2 = new Usuario();

        usuario.setName("Ivan");
        usuario2.setName("Ricardo");

        LocalDate localDate = LocalDate.of(1999, 2, 25);
        usuario.setBirthData(localDate);

        LocalDate localDate2 = LocalDate.of(2000, 12, 1);
        usuario2.setBirthData(localDate2);

        usuario.setTelephone("(81)9 99109-9496");
        usuario.setAddress("Rua Arroz");

        usuario2.setTelephone("(81)9 98109-9496");
        usuario2.setAddress("Rua Feijão");


        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuario2));


        Optional<UsuarioResponseDTO> optUsuarioDTO = usuarioService.buscarUsuario(1L);
        assertTrue(optUsuarioDTO.isPresent());

        Optional<UsuarioResponseDTO> optUsuarioDTO2 = usuarioService.buscarUsuario(2L);
        assertTrue(true);

        assertEquals("Ivan", optUsuarioDTO.get().getName());
        assertEquals(localDate, optUsuarioDTO.get().getBirthData());
        assertEquals("(81)9 99109-9496", optUsuarioDTO.get().getTelephone());
        assertEquals("Rua Arroz", optUsuarioDTO.get().getAddress());

        assertEquals("Ricardo", optUsuarioDTO2.orElseThrow(()-> new AssertionError(("Usuário não encontrado"))).getName());
        assertEquals(localDate2, optUsuarioDTO2.get().getBirthData());
        assertEquals("(81)9 98109-9496", optUsuarioDTO2.get().getTelephone());
        assertEquals("Rua Feijão", optUsuarioDTO2.get().getAddress());


    }

    @Test
    void buscarUsuarioInexistente() {

        BadRequestException b = assertThrows(BadRequestException.class, () -> usuarioService.buscarUsuario(1L));
        assertEquals("id não encontrado", b.getMessage());
    }


    @Test
    void criarUsuarioComSucesso() {

        LocalDate localDate = LocalDate.of(1999, 2, 25);
        Usuario usuario = new Usuario(1L, "Ivan", localDate, "81998745214", "Rua Augusto");
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTO("Ivan", localDate, "81998745214", "Rua Augusto");

        when(usuarioRepository.save(any())).thenReturn(usuario);

        UsuarioResponseDTO response = usuarioService.criarUsuario(requestDTO);

        assertNotNull(requestDTO);
        assertEquals("Ivan", response.getName());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));

    }

    @Test
    void criarUsuarioNomeDuplicado() {
        LocalDate localDate = LocalDate.of(1999, 2, 25);
        UsuarioRequestDTO u = new UsuarioRequestDTO();
        u.setName("Ivan");
        u.setBirthData(localDate);
        u.setAddress("Rua Rangel");
        u.setTelephone("(81)99856328");

        when(usuarioRepository.existsByName("Ivan")).thenReturn(true);


        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> usuarioService.criarUsuario(u));

        assertEquals(" Nome já cadastrado. ", badRequestException.getMessage());
    }

    @Test
    void criarUsuarioCampoFaltando() {

        UsuarioRequestDTO u = new UsuarioRequestDTO();
        u.setAddress(" Rua Rangel");
        u.setTelephone("(81) 9995584122");
        u.setName("Ivan");
        u.setBirthData(null);

        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> usuarioService.criarUsuario(u));
        assertEquals("O campo BirthData é obrigatório.", badRequestException.getMessage());

    }
}
