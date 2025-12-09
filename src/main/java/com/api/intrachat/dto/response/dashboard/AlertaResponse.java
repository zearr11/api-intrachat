package com.api.intrachat.dto.response.dashboard;

import com.api.intrachat.dto.response.UsuarioResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlertaResponse {

    private List<UsuarioResponse> jefesOperacionSinAsignar;
    private List<UsuarioResponse> supervisoresSinAsignar;
    private List<UsuarioResponse> colaboradoresSinAsignar;

}
