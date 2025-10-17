package com.api.intrachat.models.general;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "files")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFile;
    private String url;
    private String name;
    private String type;
    private Double size;

}
