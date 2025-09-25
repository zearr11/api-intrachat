package com.api.intrachat.models.communication;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "message_files")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMessageFile;

    private String fileName;
    private String fileType;
    private Integer size;
    private String urlFile;

    @OneToOne
    @JoinColumn(name = "fk_id_message", nullable = false)
    private Message message;

}
