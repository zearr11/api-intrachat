package com.api.intrachat.models.communication;

import com.api.intrachat.models.entity.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "publications")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPublication;

    @ManyToOne
    @JoinColumn(name = "fk_id_user", nullable = false)
    private User user;

    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private LocalDateTime creationDate;
    private Integer likes;

}
