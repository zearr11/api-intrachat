package com.api.intrachat.utils.dto.request;

import com.api.intrachat.utils.enums.RoleUser;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String names;
    private String lastnames;
    private String email;
    private RoleUser role;
    private Long idPosition;

}
