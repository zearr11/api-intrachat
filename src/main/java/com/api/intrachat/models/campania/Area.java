package com.api.intrachat.models.campania;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "areas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

}
