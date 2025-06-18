package com.examplo.cadastro.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Schema(description = "Entidade que representa um membro da Igreja Aliança Batista")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "igreja_alianca_batista")
public class Usuario {

    @Schema(description = "ID do membro (gerado automaticamente)",
            example = "1")
    @Column(updatable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "igreja_seq")
    @SequenceGenerator(name = "igreja_seq", sequenceName = "igreja_alianca_batista_id_seq", allocationSize = 1)
    private Long id;

    @Schema(description = "Nome completo do membro",
            example = "João Pedro da Silva")
    @Column(name = "name", nullable = false)
    private String name;

    @Schema(description = "Data de nascimento do membro",
            example = "1985-03-20")
    @Column(name = "birth_Data", nullable = false)
    private LocalDate birthData;

    @Schema(description = "Número de telefone para contato",
            example = "(11) 98765-4321")
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Schema(description = "Endereço completo do membro",
            example = "Rua São João, 123 - Jardim das Oliveiras - São Paulo/SP")
    @Column(name = "address", nullable = false)
    private String address;
}