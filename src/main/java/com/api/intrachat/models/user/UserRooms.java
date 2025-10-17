package com.api.intrachat.models.user;

import com.api.intrachat.models.room.Room;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_rooms")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUserRooms;

    @ManyToOne
    @JoinColumn(name = "fk_id_room")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "fk_id_user")
    private User user;

}
