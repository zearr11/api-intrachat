package com.api.intrachat.services.impl.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.request.EmpresaRequest;
import com.api.intrachat.dto.request.EmpresaRequest2;
import com.api.intrachat.dto.response.EmpresaResponse;
import com.api.intrachat.models.campania.Empresa;
import com.api.intrachat.repositories.campania.EmpresaRepository;
import com.api.intrachat.services.interfaces.campania.IEmpresaService;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.constants.GeneralConstants;
import com.api.intrachat.utils.exceptions.errors.ErrorException400;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import com.api.intrachat.utils.exceptions.errors.ErrorException409;
import com.api.intrachat.utils.mappers.EmpresaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
                        GeneralConstants.mensajeEntidadNoExiste("Empresa", id.toString())
                )
        );
    }

    @Override
    public List<Empresa> obtenerEmpresas() {
        return empresaRepository.findAll();
    }

    @Override
    public PaginatedResponse<List<EmpresaResponse>> obtenerEmpresasPaginado(int page, int size,
                                                                            boolean estado, String filtro) {
        if (page < 1 || size < 1) {
            throw new ErrorException400(PaginatedConstants.ERROR_PAGINA_LONGITUD_INVALIDO);
        }

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("nombreComercial").ascending());
        Page<Empresa> listado = empresaRepository.buscarPorFiltro(filtro, estado, pageable);

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

        if (empresaRepository.findByRazonSocial(empresaRequest.getRazonSocial()).isPresent())
            throw new ErrorException409(GeneralConstants.mensajeEntidadYaRegistrada("Empresa"));

        if (empresaRepository.findByNombreComercial(empresaRequest.getNombreComercial()).isPresent())
            throw new ErrorException409(GeneralConstants.mensajeEntidadYaRegistrada("Empresa"));

        if (empresaRepository.findByRuc(empresaRequest.getRuc()).isPresent())
            throw new ErrorException409(GeneralConstants.mensajeEntidadYaRegistrada("Empresa"));

        Empresa nuevaEmpresa = new Empresa(
                null, empresaRequest.getRazonSocial(), empresaRequest.getNombreComercial(),
                empresaRequest.getRuc(), empresaRequest.getCorreo(), empresaRequest.getTelefono(),
                GeneralConstants.ESTADO_DEFAULT
        );

        empresaRepository.save(nuevaEmpresa);

        return GeneralConstants.mensajeEntidadCreada("Empresa");
    }

    @Override
    public String modificarEmpresa(Long id, EmpresaRequest2 empresaRequest) {
        Empresa empresaActualizar = obtenerEmpresaPorID(id);

        // Razon Social
        if (empresaRequest.getRazonSocial() != null && !empresaRequest.getRazonSocial().isBlank()) {
            Optional<Empresa> empresaCoincidente = empresaRepository.findByRazonSocial(
                    empresaRequest.getRazonSocial());

            if (empresaCoincidente.isPresent() && !id.equals(empresaCoincidente.get().getId()))
                throw new ErrorException409(GeneralConstants.mensajeEntidadYaRegistrada("Empresa"));

            empresaActualizar.setRazonSocial(empresaRequest.getRazonSocial());
        }
        // Nombre Comercial
        if (empresaRequest.getNombreComercial() != null && !empresaRequest.getNombreComercial().isBlank()) {
            Optional<Empresa> empresaCoincidente = empresaRepository.findByNombreComercial(
                    empresaRequest.getNombreComercial());

            if (empresaCoincidente.isPresent() && !id.equals(empresaCoincidente.get().getId()))
                throw new ErrorException409(GeneralConstants.mensajeEntidadYaRegistrada("Empresa"));

            empresaActualizar.setNombreComercial(empresaRequest.getNombreComercial());
        }
        // RUC
        if (empresaRequest.getRuc() != null && !empresaRequest.getRuc().isBlank()) {
            Optional<Empresa> empresaCoincidente = empresaRepository.findByRuc(empresaRequest.getRuc());

            if (empresaCoincidente.isPresent() && !id.equals(empresaCoincidente.get().getId()))
                throw new ErrorException409(GeneralConstants.mensajeEntidadYaRegistrada("Empresa"));

            empresaActualizar.setRuc(empresaRequest.getRuc());
        }
        // Correo
        if (empresaRequest.getCorreo() != null && !empresaRequest.getCorreo().isBlank()) {
            empresaActualizar.setCorreo(empresaRequest.getCorreo());
        }
        // Telefono
        if (empresaRequest.getTelefono() != null && !empresaRequest.getTelefono().isBlank()) {
            empresaActualizar.setTelefono(empresaRequest.getTelefono());
        }
        // Estado
        if (empresaRequest.getEstado() != null) {
            empresaActualizar.setEstado(empresaRequest.getEstado());
        }

        empresaRepository.save(empresaActualizar);

        return GeneralConstants.mensajeEntidadActualizada("Empresa");
    }
}
