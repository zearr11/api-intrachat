package com.api.intrachat.models.campania;

import com.api.intrachat.models.chat.Grupo;
import com.api.intrachat.models.general.Usuario;
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
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "fk_id_supervisor")
    private Usuario supervisor;

    @OneToOne
    @JoinColumn(name = "fk_id_grupo")
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "fk_id_operacion")
    private Operacion operacion;

}
