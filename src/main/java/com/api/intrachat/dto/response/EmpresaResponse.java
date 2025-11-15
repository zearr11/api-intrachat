package com.api.intrachat.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmpresaResponse {

    private Long id;
    private String empresa;
    private Boolean estado;

}
