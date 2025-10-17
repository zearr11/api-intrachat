package com.api.intrachat.models.message;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "respond_messages")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespondMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRespondMessage;

    @OneToOne
    @JoinColumn(name = "fk_id_message")
    private Message message;

}
