package com.api.intrachat.dto.response;

import com.api.intrachat.utils.enums.Rol;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String accessToken;
    private String rol;

}
