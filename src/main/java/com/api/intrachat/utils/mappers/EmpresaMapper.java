package com.api.intrachat.utils.mappers;

import com.api.intrachat.dto.response.EmpresaResponse;
import com.api.intrachat.models.campania.Empresa;

public class EmpresaMapper {

    public static EmpresaResponse empresaResponse(Empresa empresa) {
        return new EmpresaResponse(
                empresa.getId(),
                empresa.getRazonSocial(),
                empresa.getNombreComercial(),
                empresa.getRuc(),
                empresa.getCorreo(),
                empresa.getTelefono(),
                empresa.getEstado()
        );
    }

}
