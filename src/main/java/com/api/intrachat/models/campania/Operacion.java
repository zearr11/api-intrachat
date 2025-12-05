package com.api.intrachat.models.campania;

import com.api.intrachat.models.general.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "operaciones")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Operacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_id_sede")
    private Sede sede;

    @ManyToOne
    @JoinColumn(name = "fk_id_campania")
    private Campania campania;

    @ManyToOne
    @JoinColumn(name = "fk_id_jefe_operacion")
    private Usuario jefeOperacion;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaFinalizacion;

}
