package com.api.intrachat.models.message;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "answer_messages")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnswerMessage;

    @OneToOne
    @JoinColumn(name = "fk_id_message")
    private Message message;

    @ManyToMany
    @JoinTable(
            name = "answer_to_message",
            joinColumns = @JoinColumn(name = "id_answer_message"),
            inverseJoinColumns = @JoinColumn(name = "id_respond_message")
    )
    private Set<RespondMessage> answerToMessage = new HashSet<>();

    public void addAnswerToMessage(RespondMessage respondMessage) {
        answerToMessage.add(respondMessage);
    }

}
