package com.api.intrachat.utils.dto.response;

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
