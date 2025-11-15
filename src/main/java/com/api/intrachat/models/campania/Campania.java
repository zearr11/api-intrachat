package com.api.intrachat.models.campania;

import com.api.intrachat.utils.enums.AreaAtencion;
import com.api.intrachat.utils.enums.MedioComunicacion;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "campanias")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Campania {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "fk_id_empresa")
    private Empresa empresa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AreaAtencion areaAtencion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MedioComunicacion medioComunicacion;

    @Column(nullable = false)
    private Boolean estado;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private LocalDateTime ultimaModificacion;

}
