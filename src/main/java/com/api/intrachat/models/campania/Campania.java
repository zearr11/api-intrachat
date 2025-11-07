package com.api.intrachat.models.campania;

import com.api.intrachat.utils.enums.MedioComunicacion;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name = "fk_id_area")
    private Area area;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MedioComunicacion medioComunicacion;

    @Column(nullable = false)
    private Boolean estado;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private LocalDateTime ultimaModificacion;

    @ManyToMany
    @JoinTable(
            name = "campania_sedes",
            joinColumns = @JoinColumn(name = "id_campania"),
            inverseJoinColumns = @JoinColumn(name = "id_sede")
    )
    private Set<Sede> campaniaSedes = new HashSet<>();

    public void addCampaniaSedes(Sede sede) {
        campaniaSedes.add(sede);
    }

}
