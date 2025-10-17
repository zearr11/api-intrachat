package com.api.intrachat.models.message;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "message_types")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMessageType;
    private String type;

}
