package com.examplo.cadastro.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Schema(description = "DTO para resposta com dados do usuário")
@Setter
@Getter
public class UsuarioResponseDTO {

    @Schema(description = "Identificador único do usuário",
            example = "1")
    private Long id;

    @Schema(description = "Nome completo do usuário",
            example = "Maria Silva Santos")
    private String name;

    @Schema(description = "Data de nascimento no formato dd-MM-yyyy",
            example = "15-05-1990")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthData;

    @Schema(description = "Número de telefone do usuário",
            example = "(11) 98765-4321")
    private String telephone;

    @Schema(description = "Endereço completo do usuário",
            example = "Rua das Flores, 123 - Jardim Primavera - São Paulo/SP")
    private String address;
}