package com.api.intrachat.models.campania;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sedes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Sede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String direccion;

    @Column(nullable = false)
    private Integer numeroPostal;

    @Column(nullable = false)
    private String departamento;

    @Column(nullable = false)
    private String provincia;

    @Column(nullable = false)
    private String distrito;

    @Column(nullable = false)
    private Boolean estado;

}
