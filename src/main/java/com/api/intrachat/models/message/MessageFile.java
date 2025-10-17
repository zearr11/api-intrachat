package com.api.intrachat.models.message;

import com.api.intrachat.models.general.File;
import com.api.intrachat.models.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "message_file")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMessageFile;

    @OneToOne
    @JoinColumn(name = "fk_id_user")
    private User user;

    @OneToOne
    @JoinColumn(name = "fk_id_file")
    private File file;

}
