package com.api.intrachat.models.chat;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "textos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Texto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contenido;

    @OneToOne
    @JoinColumn(name = "fk_id_mensaje")
    private Mensaje mensaje;

}
