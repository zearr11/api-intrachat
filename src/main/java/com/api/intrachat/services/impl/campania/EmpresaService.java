package com.api.intrachat.services.impl.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.request.EmpresaRequest;
import com.api.intrachat.dto.response.AreaResponse;
import com.api.intrachat.dto.response.EmpresaResponse;
import com.api.intrachat.models.campania.Area;
import com.api.intrachat.models.campania.Empresa;
import com.api.intrachat.models.campania.Pais;
import com.api.intrachat.repositories.campania.EmpresaRepository;
import com.api.intrachat.services.interfaces.campania.IEmpresaService;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.constants.ResponseConstants;
import com.api.intrachat.utils.exceptions.errors.ErrorException400;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import com.api.intrachat.utils.exceptions.errors.ErrorException409;
import com.api.intrachat.utils.mappers.AreaMapper;
import com.api.intrachat.utils.mappers.EmpresaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService implements IEmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Override
    public Empresa obtenerEmpresaPorID(Long id) {
        return empresaRepository.findById(id).orElseThrow(
                () -> new ErrorException404(
                        ResponseConstants.mensajeEntidadNoExiste("Empresa", id.toString())
                )
        );
    }

    @Override
    public List<Empresa> obtenerEmpresas() {
        return empresaRepository.findAll();
    }

    @Override
    public PaginatedResponse<List<EmpresaResponse>> obtenerEmpresasPaginado(int page, int size) {

        if (page < 1 || size < 1) {
            throw new ErrorException400(PaginatedConstants.ERROR_PAGINA_LONGITUD_INVALIDO);
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Empresa> listado = empresaRepository.findAll(pageable);

        List<EmpresaResponse> empresas = listado.getContent()
                .stream()
                .map(EmpresaMapper::empresaResponse)
                .toList();

        return new PaginatedResponse<>(
                page,
                size,
                empresas.size(),
                listado.getTotalElements(),
                listado.getTotalPages(),
                empresas
        );
    }

    @Override
    public String crearEmpresa(EmpresaRequest empresaRequest) {

        if (empresaRepository.findByNombre(empresaRequest.getNombre()).isPresent())
            throw new ErrorException409(ResponseConstants.mensajeEntidadYaRegistrada("Empresa"));

        Empresa nuevaEmpresa = Empresa.builder()
                .nombre(empresaRequest.getNombre())
                .build();
        empresaRepository.save(nuevaEmpresa);

        return ResponseConstants.mensajeEntidadCreada("Empresa");
    }

    @Override
    public String modificarEmpresa(Long id, EmpresaRequest empresaRequest) {

        Empresa empresaActualizar = obtenerEmpresaPorID(id);

        // Nombre
        if (empresaRequest.getNombre() != null && !empresaRequest.getNombre().isBlank()) {
            Optional<Empresa> empresaCoincidente = empresaRepository.findByNombre(empresaRequest.getNombre());

            if (empresaCoincidente.isPresent() && !id.equals(empresaCoincidente.get().getId()))
                throw new ErrorException409(ResponseConstants.mensajeEntidadYaRegistrada("Empresa"));

            empresaActualizar.setNombre(empresaRequest.getNombre());
        }

        empresaRepository.save(empresaActualizar);

        return ResponseConstants.mensajeEntidadActualizada("Empresa");
    }
}
