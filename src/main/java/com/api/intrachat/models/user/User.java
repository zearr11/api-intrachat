package com.api.intrachat.models.user;

import com.api.intrachat.models.agrupation.Area;
import com.api.intrachat.models.agrupation.Campaign;
import com.api.intrachat.models.agrupation.Headquarters;
import com.api.intrachat.models.general.File;
import com.api.intrachat.utils.enums.RoleSystem;
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

    private String names;
    private String lastnames;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String numberPhone;

    private LocalDateTime creationDate;
    private LocalDateTime lastModification;

    private String password;
    private Boolean isEnabled;
    private String info;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleSystem role;

    @ManyToOne
    @JoinColumn(name = "fk_id_position", nullable = false)
    private Position position;

    @OneToOne
    @JoinColumn(name = "fk_id_file", nullable = false)
    private File file;

    // News
    @ManyToOne
    @JoinColumn(name = "fk_id_campaign", nullable = false)
    private Campaign campaign;

    @ManyToOne
    @JoinColumn(name = "fk_id_headquarters", nullable = false)
    private Headquarters headquarters;

    @ManyToOne
    @JoinColumn(name = "fk_id_area", nullable = false)
    private Area area;

}
