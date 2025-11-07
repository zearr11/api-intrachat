package com.api.intrachat.models.general;

import com.api.intrachat.utils.enums.Genero;
import com.api.intrachat.utils.enums.TipoDoc;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "personas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDoc tipoDoc;

    @Column(nullable = false)
    private String numeroDoc;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genero genero;

    @Column(nullable = false, unique = true)
    private String celular;

    @Column(nullable = false)
    private String informacion;

}
