package com.api.intrachat.utils.dto.response.entities.ok;

import com.api.intrachat.utils.enums.RoleSystem;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long idUser;
    private String urlPhoto; // Llave foranea

    private String names;
    private String lastnames;
    private String email;
    private String numberPhone;

    private RoleSystem role; // Enum (Parte del codigo)

    // fk key
    private String position;
    private String headquarters;
    private String campaign;
    private String area;

    private LocalDateTime creationDate;
    private LocalDateTime lastModification;

}
