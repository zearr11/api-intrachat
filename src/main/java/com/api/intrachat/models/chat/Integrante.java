package com.api.intrachat.models.chat;

import com.api.intrachat.models.general.Usuario;
import com.api.intrachat.utils.enums.Permiso;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "integrantes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Integrante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Permiso permiso;

    @Column(nullable = false)
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "fk_id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "fk_id_sala")
    private Sala sala;

}
