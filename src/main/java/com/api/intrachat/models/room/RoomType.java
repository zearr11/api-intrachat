package com.api.intrachat.models.room;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room_types")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRoomType;
    private String type;

}
