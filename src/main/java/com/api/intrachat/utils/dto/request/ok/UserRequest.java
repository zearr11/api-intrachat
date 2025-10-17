package com.api.intrachat.utils.dto.request.ok;

import com.api.intrachat.utils.enums.RoleSystem;
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
    private String numberPhone;
    private RoleSystem role;

    // fk key
    private Long idCampaign;
    private Long idArea;
    private Long idHeadquarters;
    private Long idPosition;

}
