package com.api.intrachat.models.communication;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "publication_files")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublicationFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPublicationFile;

    @ManyToOne
    @JoinColumn(name = "fk_id_publication", nullable = false)
    private Publication publication;

    private String urlFile;
    private String fileType;

}
