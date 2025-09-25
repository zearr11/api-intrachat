package com.api.intrachat.utils.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long idUser;
    private String urlPhoto;

    private String names;
    private String lastnames;
    private String email;

    private String positionName;
    private String roleName;

    private LocalDateTime creationDate;
    private LocalDateTime lastModification;

}
