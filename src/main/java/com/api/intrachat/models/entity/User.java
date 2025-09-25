package com.api.intrachat.models.entity;

import com.api.intrachat.utils.enums.RoleUser;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    private String urlPhoto;
    private String names;
    private String lastnames;
    private String email;
    private String password;
    private Boolean isEnabled;
    private LocalDateTime creationDate;
    private LocalDateTime lastModification;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleUser role;

    @ManyToOne
    @JoinColumn(name = "fk_id_position", nullable = false)
    private Position position;

}
