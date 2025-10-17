package com.api.intrachat.models.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "positions")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPosition;
    private String positionName;

}
