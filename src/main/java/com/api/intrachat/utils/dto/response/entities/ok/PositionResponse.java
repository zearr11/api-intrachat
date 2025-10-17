package com.api.intrachat.utils.dto.response.entities.ok;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionResponse {

    private Long idPosition;
    private String positionName;

}
