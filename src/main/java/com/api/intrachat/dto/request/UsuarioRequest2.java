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
public class UsuarioRequest2 {

    private String nombres;
    private String apellidos;
    private TipoDoc tipoDoc;
    private String numeroDoc;
    private Genero genero;
    private String celular;
    private String informacion;

    private String email;
    private String password;
    private Cargo cargo;
    private Rol rol;
    private Boolean estado;

}
