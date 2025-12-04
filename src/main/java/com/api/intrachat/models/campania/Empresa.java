package com.api.intrachat.models.campania;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "empresas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String razonSocial;

    @Column(nullable = false, unique = true)
    private String nombreComercial;

    @Column(nullable = false, unique = true)
    private String ruc;

    @Column(nullable = false)
    private String correo;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private Boolean estado;

}
