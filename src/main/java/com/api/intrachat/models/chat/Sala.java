package com.api.intrachat.models.chat;

import com.api.intrachat.utils.enums.TipoSala;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "salas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSala tipoSala;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

}
