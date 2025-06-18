package com.examplo.meuprojeto.integration;


import com.examplo.meuprojeto.dto.UsuarioRequestDTO;
import com.examplo.meuprojeto.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UsuarioResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        // Cria alguns usuários para testar a listagem
        UsuarioRequestDTO usuario1 = new UsuarioRequestDTO();
        usuario1.setName("João Silva");
        usuario1.setBirthData(LocalDate.of(1990, 1, 15));
        usuario1.setTelephone("11999999999");
        usuario1.setAddress("Rua A, 123");

        UsuarioRequestDTO usuario2 = new UsuarioRequestDTO();
        usuario2.setName("Maria Santos");
        usuario2.setBirthData(LocalDate.of(1985, 6, 20));
        usuario2.setTelephone("11988888888");
        usuario2.setAddress("Av B, 456");

        UsuarioRequestDTO usuario3 = new UsuarioRequestDTO();
        usuario3.setName("Pedro Oliveira");
        usuario3.setBirthData(LocalDate.of(1995, 12, 3));
        usuario3.setTelephone("11977777777");
        usuario3.setAddress("Rua C, 789");

        // Salva os usuários no banco
        usuarioService.criarUsuario(usuario1);
        usuarioService.criarUsuario(usuario2);
        usuarioService.criarUsuario(usuario3);
    }

    @Test
    @DisplayName("Deve listar todos os usuários com sucesso")
    void deveListarTodosUsuarios() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/usuarios")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].birthData").exists())
                .andExpect(jsonPath("$[0].telephone").exists())
                .andExpect(jsonPath("$[0].address").exists())
                .andExpect(jsonPath("$[1].id").exists())
                .andExpect(jsonPath("$[2].id").exists());
    }

}
