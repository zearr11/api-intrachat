package com.api.intrachat.models.mode;

import com.api.intrachat.models.communication.Message;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chat_individual")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Individual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIndividual;
    private LocalDateTime creationDate;

    @ManyToMany
    @JoinTable(
            name = "individual_messages",
            joinColumns = @JoinColumn(name = "id_individual"),
            inverseJoinColumns = @JoinColumn(name = "id_message")
    )
    private Set<Message> messages = new HashSet<>();

    public void addMessage(Message message) {
        this.messages.add(message);
    }

}
