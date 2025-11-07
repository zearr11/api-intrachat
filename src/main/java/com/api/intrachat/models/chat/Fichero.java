package com.api.intrachat.models.chat;

import com.api.intrachat.models.general.Archivo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ficheros")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Fichero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "fk_id_mensaje")
    private Mensaje mensaje;

    @OneToOne
    @JoinColumn(name = "fk_id_archivo")
    private Archivo archivo;

}
