package com.api.intrachat.models.room;

import com.api.intrachat.models.general.File;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room_details")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRoomDetails;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String roomName;

    @OneToOne
    @JoinColumn(name = "fk_id_room")
    private Room room;

    @OneToOne
    @JoinColumn(name = "fk_id_file")
    private File file;

}
