package com.api.intrachat.models.campania;

import com.api.intrachat.models.chat.Grupo;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private Integer numeroEquipo;

    @OneToOne
    @JoinColumn(name = "fk_id_grupo")
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "fk_id_campania")
    private Campania campania;

    @ManyToOne
    @JoinColumn(name = "fk_id_sede")
    private Sede sede;

}
