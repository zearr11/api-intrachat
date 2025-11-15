package com.api.intrachat.models.chat;

import com.api.intrachat.models.general.Archivo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "grupos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @OneToOne
    @JoinColumn(name = "fk_id_archivo")
    private Archivo imagenGrupo;

    @OneToOne
    @JoinColumn(name = "fk_id_sala")
    private Sala sala;

    @Column(nullable = false)
    private LocalDateTime ultimaModificacion;

    @Column(nullable = false)
    private Boolean estado;

}
