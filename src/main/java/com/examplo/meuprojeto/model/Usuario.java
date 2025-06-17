package com.examplo.meuprojeto.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "igreja_alianca_batista")
public class Usuario {

    @Column(updatable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "igreja_seq")
    @SequenceGenerator(name = "igreja_seq", sequenceName = "igreja_alianca_batista_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthData", nullable = false)
    private LocalDate birthData;

    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Column(name = "address", nullable = false)
    private String address;
}
