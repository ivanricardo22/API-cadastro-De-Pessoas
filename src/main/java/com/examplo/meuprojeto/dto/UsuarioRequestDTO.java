package com.examplo.meuprojeto.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data
@Getter
@Setter
public class UsuarioRequestDTO {
    private String nome;
    private String email;
    private Integer idade;

}
