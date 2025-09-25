package com.api.intrachat.models.mode;

import com.api.intrachat.utils.enums.RoleGroup;
import com.api.intrachat.models.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "group_has_users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGroupUser;

    @ManyToOne
    @JoinColumn(name = "fk_id_user", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "fk_id_group", nullable = false)
    private Group group;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleGroup roleGroup;

}
