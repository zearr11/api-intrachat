package com.api.intrachat.dto.request;

import com.api.intrachat.utils.enums.Cargo;
import com.api.intrachat.utils.enums.Genero;
import com.api.intrachat.utils.enums.Rol;
import com.api.intrachat.utils.enums.TipoDoc;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioRequest {

    private String nombres;
    private String apellidos;
    private TipoDoc tipoDoc;
    private String numeroDoc;
    private Genero genero;
    private String celular; // unique

    private String email; // unique
    private Cargo cargo;
    private Rol rol;

}
