package com.examplo.meuprojeto.dto;


import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UsuarioResponseDTO {

    private Long id;
    private String name;
    private Date birthData;
    private String telephone;
    private String address;

}
