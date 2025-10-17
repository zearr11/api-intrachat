package com.api.intrachat.models.room;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRoom;

    private LocalDateTime creationDate;
    private LocalDateTime lastModification;

    @ManyToOne
    @JoinColumn(name = "fk_id_room_type")
    private RoomType roomType;

}
