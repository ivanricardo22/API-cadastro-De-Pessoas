package com.examplo.meuprojeto;

import com.examplo.meuprojeto.dto.UsuarioRequestDTO;
import com.examplo.meuprojeto.dto.UsuarioResponseDTO;
import com.examplo.meuprojeto.entity.Usuario;
import com.examplo.meuprojeto.repository.UsuarioRepository;
import com.examplo.meuprojeto.service.UsuarioService;
import com.examplo.meuprojeto.service.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;



public class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void listarUsuariosComSucesso(){

        LocalDate localDate = LocalDate.of(1999, 2, 25);
        Date dataNascimento = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        LocalDate localDate2 = LocalDate.of(2000, 12, 1);
        Date dataNascimento2 = Date.from(localDate2.atStartOfDay(ZoneId.systemDefault()).toInstant());



        Usuario usuario1 = new Usuario(1L,"Ivan",dataNascimento ,"(81)9 99109-9496","Rua Arroz");
        Usuario usuario2 = new Usuario(2L,"Ricardo",dataNascimento2 ,"(81)9 98109-9496","Rua Feijão");


        List<Usuario> usuarios = List.of(usuario1,usuario2);

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioResponseDTO> resultado = usuarioService.listarUsuarios();

        assertNotNull(resultado);
        assertEquals(2,resultado.size());
        assertEquals("Ivan",resultado.stream().filter(u -> u.getId() == 1L).findFirst().get().getNome());
        assertEquals(dataNascimento,resultado.stream().filter(u -> u.getId() == 1L ).findFirst().get().getDataDeAniversario());
        assertEquals("(81)9 99109-9496",resultado.stream().filter(u -> u.getId() == 1L ).findFirst().get().getTelefone());
        assertEquals("Rua Arroz",resultado.stream().filter(u -> u.getId().equals(1L)).findFirst().get().getEndereco());

        assertNotNull(resultado);
        assertEquals(2,resultado.size());
        assertEquals("Ricardo",resultado.stream().filter(u -> u.getId() == 2L).findFirst().get().getNome());
        assertEquals(dataNascimento2,resultado.stream().filter(u -> u.getId() == 2L ).findFirst().get().getDataDeAniversario());
        assertEquals("(81)9 98109-9496",resultado.stream().filter(u -> u.getId() == 2L ).findFirst().get().getTelefone());
        assertEquals("Rua Feijão",resultado.stream().filter(u -> u.getId().equals(2L)).findFirst().get().getEndereco());

    }

    @Test
    public void listarUsuariosSemTerUsuarios() {

        List<Usuario> usuarios =  new ArrayList<>();

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioResponseDTO> resultado = usuarioService.listarUsuarios();

        assertNotNull(resultado);
        assertEquals(0,resultado.size());

    }

    @Test
    public void buscarUsuarioComSucesso() {

        Usuario usuario = new Usuario();
        Usuario usuario2 = new Usuario();

        usuario.setNome("Ivan");
        usuario2.setNome("Ricardo");

        LocalDate localDate = LocalDate.of(1999, 2, 25);
        Date dataNascimento = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        usuario.setDataDeAniversario(dataNascimento);

        LocalDate localDate2 = LocalDate.of(2000, 12, 1);
        Date dataNascimento2 = Date.from(localDate2.atStartOfDay(ZoneId.systemDefault()).toInstant());
        usuario2.setDataDeAniversario(dataNascimento2);

        usuario.setTelefone("(81)9 99109-9496");
        usuario.setEndereco("Rua Arroz");

        usuario2.setTelefone("(81)9 98109-9496");
        usuario2.setEndereco("Rua Feijão");


        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuario2));


        Optional<UsuarioResponseDTO>  optUsuarioDTO = usuarioService.buscarUsuario(1L);
        assertTrue(optUsuarioDTO.isPresent());

        Optional<UsuarioResponseDTO>  optUsuarioDTO2 = usuarioService.buscarUsuario(2L);
        assertTrue(true);

        assertEquals("Ivan", optUsuarioDTO.get().getNome());
        assertEquals(dataNascimento,optUsuarioDTO.get().getDataDeAniversario());
        assertEquals("(81)9 99109-9496",optUsuarioDTO.get().getTelefone());
        assertEquals("Rua Arroz", optUsuarioDTO.get().getEndereco());

        assertEquals("Ricardo", optUsuarioDTO2.get().getNome());
        assertEquals(dataNascimento2,optUsuarioDTO2.get().getDataDeAniversario());
        assertEquals("(81)9 98109-9496",optUsuarioDTO2.get().getTelefone());
        assertEquals("Rua Feijão", optUsuarioDTO2.get().getEndereco());


    }

    @Test
    public void buscarUsuarioENaoEncontraUsuario() {

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        when(usuarioRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<UsuarioResponseDTO>  optUsuarioDTO = usuarioService.buscarUsuario(1L);
        assertTrue(optUsuarioDTO.isEmpty());

        Optional<UsuarioResponseDTO>  optUsuarioDTO2 = usuarioService.buscarUsuario(2L);
        assertTrue(optUsuarioDTO2.isEmpty());
    }


    @Test
    public void criarUsuarioComSucesso() {

        LocalDate localDate = LocalDate.of(1999, 2, 25);
        Date dataNascimento = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        LocalDate localDate2 = LocalDate.of(2000, 12, 1);
        Date dataNascimento2 = Date.from(localDate2.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Dados de entrada
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO("Ivan",dataNascimento,"(81)9 99109-9496","Rua Arroz");
        UsuarioRequestDTO usuarioRequestDTO2 = new UsuarioRequestDTO("Ricardo",dataNascimento2,"(81)9 98109-9496","Rua Feijão");


        // Objeto de retorno esperado do mock
        Usuario usuarioMock = new Usuario(1L, "Ivan",dataNascimento,"(81)9 99109-9496","Rua Arroz");
        Usuario usuarioMock2 = new Usuario(2L, "Ricardo",dataNascimento,"(81)9 98109-9496","Rua Feijão");

        // Mock do repositório com comportamentos diferentes

        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
                    Usuario usuario = invocation.getArgument(0);
                    if (usuario.getNome().equals("Ivan")) {
                        usuario.setId(1L);
                        return usuario;
                    } else {
                        usuario.setId(2L);
                        return usuario;
                    }
                });



        UsuarioResponseDTO usuarioSalvo = usuarioService.criarUsuario(usuarioRequestDTO);

        assertNotNull(usuarioSalvo); // Verifica se o usuário salvo não é nulo
        assertEquals(1L, usuarioSalvo.getId()); // Verifica se o ID do usuário salvo é 1L
        assertEquals("Ivan", usuarioSalvo.getNome()); // Verifica se o nome do usuário salvo é "Ivan"
        assertEquals(dataNascimento, usuarioSalvo.getDataDeAniversario()); // Verifica se a data  do usuário salvo é dataNascimento"
        assertEquals("(81)9 99109-9496", usuarioSalvo.getTelefone()); // Verifica se o telefone do usuário salvo é "(81)9 99109-9496"
        assertEquals("Rua Arroz", usuarioSalvo.getEndereco()); // verificar se o endereço do usuário salvo é "Rua Arroz"

        UsuarioResponseDTO usuarioSalvo2 = usuarioService.criarUsuario(usuarioRequestDTO2);

        assertNotNull(usuarioSalvo); // Verifica se o usuário salvo não é nulo
        assertEquals(2L, usuarioSalvo2.getId()); // Verifica se o ID do usuário salvo é 1L
        assertEquals("Ricardo", usuarioSalvo2.getNome()); // Verifica se o nome do usuário salvo é "Ivan"
        assertEquals(dataNascimento2, usuarioSalvo2.getDataDeAniversario()); // Verifica se a data  do usuário salvo é dataNascimento"
        assertEquals("(81)9 98109-9496", usuarioSalvo2.getTelefone()); // Verifica se o telefone do usuário salvo é "(81)9 99109-9496"
        assertEquals("Rua Feijão", usuarioSalvo2.getEndereco()); // verificar se o endereço do usuário salvo é "Rua Arroz"
    }

    @Test
    public void criarUsuarioSemSucesso() {

        LocalDate localDate = LocalDate.of(1999, 2, 25);
        Date dataNascimento = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        LocalDate localDate2 = LocalDate.of(1999, 2, 25);
        Date dataNascimento2 = Date.from(localDate2.atStartOfDay(ZoneId.systemDefault()).toInstant());


        // Dados de entrada
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO("Ivan",dataNascimento, "(81)9 99109-9496","Rua Arroz" );
        UsuarioRequestDTO usuarioRequestDTO2 = new UsuarioRequestDTO("Ricardo",dataNascimento2, "(81)9 98109-9496","Rua Feijão" );


        // Configuração do mock para lançar uma exceção
        doThrow(new DataIntegrityViolationException("Erro ao salvar o usuário")).when(usuarioRepository).save(any(Usuario.class));


        // Verificar se a exceção é lançada
        assertThrows(RuntimeException.class, () -> usuarioService.criarUsuario(usuarioRequestDTO));
        assertThrows(RuntimeException.class, () -> usuarioService.criarUsuario(usuarioRequestDTO2));


    }




}
