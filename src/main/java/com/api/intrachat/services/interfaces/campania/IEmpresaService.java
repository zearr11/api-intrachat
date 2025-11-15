package com.api.intrachat.services.interfaces.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.request.EmpresaRequest;
import com.api.intrachat.dto.request.EmpresaRequest2;
import com.api.intrachat.dto.response.EmpresaResponse;
import com.api.intrachat.models.campania.Empresa;
import java.util.List;

public interface IEmpresaService {

    Empresa obtenerEmpresaPorID(Long id);
    List<Empresa> obtenerEmpresas();

    PaginatedResponse<List<EmpresaResponse>> obtenerEmpresasPaginado(int page, int size,
                                                                     boolean estado, String filtro);

    String crearEmpresa(EmpresaRequest empresaRequest);
    String modificarEmpresa(Long id, EmpresaRequest2 empresaRequest);

}
