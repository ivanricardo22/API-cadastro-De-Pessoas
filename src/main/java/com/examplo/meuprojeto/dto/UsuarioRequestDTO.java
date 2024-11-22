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

    private String name;
    private Date birthData;
    private String telephone;
    private String address;

}
