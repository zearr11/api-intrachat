package com.api.intrachat.models.general;

import com.api.intrachat.models.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    private LocalDateTime creationDate;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "fk_id_user", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "likes_from_users",
            joinColumns = @JoinColumn(name = "id_publication"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private Set<User> likes = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "publications_has_files",
            joinColumns = @JoinColumn(name = "id_publication"),
            inverseJoinColumns = @JoinColumn(name = "id_file")
    )
    private Set<File> files = new HashSet<>();

    public void addFiles(File file) {
        files.add(file);
    }

    public void addLikes(User user) {
        likes.add(user);
    }

}
