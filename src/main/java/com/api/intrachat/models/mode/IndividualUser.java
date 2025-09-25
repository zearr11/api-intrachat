package com.api.intrachat.models.mode;

import com.api.intrachat.models.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "individual_has_users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndividualUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIndividualUser;

    @ManyToOne
    @JoinColumn(name = "fk_id_user", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "fk_id_individual", nullable = false)
    private Individual individual;

}
