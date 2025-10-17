package com.api.intrachat.models.agrupation;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "areas")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArea;
    private String areaName;

}
