package com.api.intrachat.models.general;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "archivos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Archivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private Double tamanio;

}
