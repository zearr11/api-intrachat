package com.api.intrachat.dto.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SalaRequest2 {

    private List<IntegranteRequest2> integrantes;

}
