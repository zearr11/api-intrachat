package com.api.intrachat.models.campania;

import com.api.intrachat.models.general.Usuario;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "fk_id_sede")
    private Sede sede;

    @ManyToOne
    @JoinColumn(name = "fk_id_campania")
    private Campania campania;

    @ManyToOne
    @JoinColumn(name = "fk_id_jefe_operacion")
    private Usuario jefeOperacion;

}
