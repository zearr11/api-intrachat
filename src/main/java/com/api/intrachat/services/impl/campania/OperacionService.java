package com.api.intrachat.services.impl.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.request.OperacionRequest;
import com.api.intrachat.dto.response.OperacionResponse;
import com.api.intrachat.models.campania.Campania;
import com.api.intrachat.models.campania.Operacion;
import com.api.intrachat.models.general.Usuario;
import com.api.intrachat.repositories.campania.OperacionRepository;
import com.api.intrachat.services.interfaces.campania.ICampaniaService;
import com.api.intrachat.services.interfaces.campania.IEmpresaService;
import com.api.intrachat.services.interfaces.campania.IOperacionService;
import com.api.intrachat.services.interfaces.general.IUsuarioService;
import com.api.intrachat.utils.constants.GeneralConstants;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.exceptions.errors.ErrorException400;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import com.api.intrachat.utils.mappers.OperacionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OperacionService implements IOperacionService {

    private final OperacionRepository operacionRepository;
    private final ICampaniaService campaniaService;
    private final IEmpresaService empresaService;
    private final IUsuarioService usuarioService;

    public OperacionService(OperacionRepository operacionRepository,
                            ICampaniaService campaniaService,
                            IEmpresaService empresaService,
                            IUsuarioService usuarioService) {
        this.operacionRepository = operacionRepository;
        this.campaniaService = campaniaService;
        this.empresaService = empresaService;
        this.usuarioService = usuarioService;
    }

    @Override
    public Operacion obtenerOperacionPorID(Long id) {
        return operacionRepository.findById(id).orElseThrow(
                () -> new ErrorException404(
                        GeneralConstants.mensajeEntidadNoExiste("Operación", id.toString())
                )
        );
    }

    @Override
    public PaginatedResponse<List<OperacionResponse>> obtenerOperacionesPaginado(int page, int size,
                                                                                 boolean estado, Long idCampania,
                                                                                 Long idJefeOperacion) {
        if (page < 1 || size < 1) {
            throw new ErrorException400(PaginatedConstants.ERROR_PAGINA_LONGITUD_INVALIDO);
        }

        // Si no existen los servicios lanzan una excepción
        Usuario jefeOperacion = usuarioService.obtenerUsuarioPorID(idJefeOperacion);
        Campania campania = campaniaService.obtenerCampaniaPorID(idCampania);

        Pageable pageable = PageRequest.of(
                page - 1, size, Sort.by("campania.nombre").ascending());

        Page<Operacion> listado = operacionRepository.buscarOperacionesPaginado(
                estado,
                idCampania,
                idJefeOperacion,
                pageable
        );

        List<OperacionResponse> operaciones = listado.getContent()
                .stream()
                .map(OperacionMapper::operacionResponse)
                .toList();

        return new PaginatedResponse<>(
                page,
                size,
                operaciones.size(),
                listado.getTotalElements(),
                listado.getTotalPages(),
                operaciones
        );
    }

    @Override
    public String crearOperacion(OperacionRequest operacionRequest) {
        return "";
    }

    @Override
    public String finalizarOperacion(Long idOperacion) {
        return "";
    }

}
