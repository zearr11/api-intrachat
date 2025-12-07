package com.api.intrachat.models.general;

import com.api.intrachat.utils.enums.Cargo;
import com.api.intrachat.utils.enums.Rol;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cargo cargo;

    @Column(nullable = false)
    private Boolean estado;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private LocalDateTime ultimaModificacion;

    @ManyToOne
    @JoinColumn(name = "fk_id_archivo")
    private Archivo imagenPerfil;

    @OneToOne
    @JoinColumn(name = "fk_id_persona")
    private Persona persona;

}
