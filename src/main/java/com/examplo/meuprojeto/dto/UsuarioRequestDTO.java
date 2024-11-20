package com.examplo.meuprojeto.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.util.Date;

//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    private String nome;
    private Date dataDeAniversario;
    private String telefone;
    private String endereco;

}
