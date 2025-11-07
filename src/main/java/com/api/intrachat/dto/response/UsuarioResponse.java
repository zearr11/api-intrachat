package com.api.intrachat.dto.response;

import com.api.intrachat.utils.enums.Genero;
import com.api.intrachat.utils.enums.Rol;
import com.api.intrachat.utils.enums.TipoDoc;
import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioResponse {

    private Long id;
    private String urlFoto;

    private String nombres;
    private String apellidos;
    private TipoDoc tipoDoc;
    private String numeroDoc;
    private Genero genero;

    private String celular;
    private String email;

    private Rol rol;

//    private String pais;
//    private String sede;
//    private String campania;
//    private Integer numeroEquipo;

    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private Boolean estado;

}
