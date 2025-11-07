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

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String ciudad;

    @ManyToOne
    @JoinColumn(name = "fk_id_pais")
    private Pais pais;

}
