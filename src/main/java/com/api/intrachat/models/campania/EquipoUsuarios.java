package com.api.intrachat.models.campania;

import com.api.intrachat.models.general.Usuario;
import com.api.intrachat.utils.enums.Permiso;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "equipo_usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EquipoUsuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Permiso permiso;

    @Column(nullable = false)
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "fk_id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "fk_id_equipo")
    private Equipo equipo;

}
