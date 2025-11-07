package com.api.intrachat.models.chat;

import com.api.intrachat.models.general.Usuario;
import com.api.intrachat.utils.enums.TipoMensaje;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensajes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMensaje tipo;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private LocalDateTime ultimaModificacion;

    @ManyToOne
    @JoinColumn(name = "fk_id_sala")
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "fk_id_usuario")
    private Usuario usuario;

}
