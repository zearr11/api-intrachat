package com.api.intrachat.models.mode;

import com.api.intrachat.models.communication.Message;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chat_group")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGroup;

    private String urlPhoto;
    private String groupName;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime lastModification;

    @ManyToMany
    @JoinTable(
            name = "group_messages",
            joinColumns = @JoinColumn(name = "id_group"),
            inverseJoinColumns = @JoinColumn(name = "id_message")
    )
    private Set<Message> messages = new HashSet<>();

}
