package com.examplo.meuprojeto.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private Integer idade;

}
