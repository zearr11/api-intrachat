package com.api.intrachat.models.room;

import com.api.intrachat.models.user.User;
import com.api.intrachat.utils.enums.UserRoleRoom;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room_permits")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomPermits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRoomPermits;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoleRoom userRoleRoom;

    @ManyToOne
    @JoinColumn(name = "fk_id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "fk_id_room_details")
    private RoomDetails roomDetails;

}
