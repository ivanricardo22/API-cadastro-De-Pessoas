package com.examplo.meuprojeto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String nome;

    @Column(name = "BIRTH_DATA", nullable = false)
    private Date dataDeAniversario;

    @Column(name = "TELEPHONE")
    private String telefone;

    @Column(name = "ADDRESS")
    private String endereco;

    public Usuario(Long id, String nome, Date dataDeAniversario, String telefone, String endereco) {
        this.id = id;
        this.nome = nome;
        this.dataDeAniversario = dataDeAniversario;
        this.telefone = telefone;
        this.endereco = endereco;
    }



}
