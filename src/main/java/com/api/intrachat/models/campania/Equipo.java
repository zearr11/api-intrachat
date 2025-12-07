package com.api.intrachat.models.campania;

import com.api.intrachat.models.chat.Grupo;
import com.api.intrachat.models.general.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "equipos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_id_supervisor")
    private Usuario supervisor;

    @OneToOne
    @JoinColumn(name = "fk_id_grupo")
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "fk_id_operacion")
    private Operacion operacion;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaCierre;

}
