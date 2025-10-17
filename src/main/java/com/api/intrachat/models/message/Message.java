package com.api.intrachat.models.message;

import com.api.intrachat.models.room.Room;
import com.api.intrachat.models.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMessage;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private LocalDateTime dateSend;
    private LocalDateTime lastModification;

    private Boolean isReply;

    @ManyToOne
    @JoinColumn(name = "fk_id_message_type")
    private MessageType messageType;

    @ManyToOne
    @JoinColumn(name = "fk_id_room")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "fk_id_user")
    private User user;

}
