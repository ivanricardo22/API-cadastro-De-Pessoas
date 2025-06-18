package com.examplo.cadastro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Schema(description = "DTO para requisição de dados do usuário")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    @Schema(description = "Nome completo do usuário",
            example = "Maria Silva Santos")
    private String name;

    @Schema(description = "Data de nascimento",
            example = "1990-05-15")
    private LocalDate birthData;

    @Schema(description = "Número de telefone do usuário",
            example = "(11) 98765-4321")
    private String telephone;

    @Schema(description = "Endereço completo do usuário",
            example = "Rua das Flores, 123 - Jardim Primavera - São Paulo/SP")
    private String address;
}