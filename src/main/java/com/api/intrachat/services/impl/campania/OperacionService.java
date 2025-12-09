package com.api.intrachat.services.impl.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.request.OperacionRequest;
import com.api.intrachat.dto.response.OperacionEspecialResponse;
import com.api.intrachat.models.campania.Campania;
import com.api.intrachat.models.campania.Equipo;
import com.api.intrachat.models.campania.Operacion;
import com.api.intrachat.models.campania.Sede;
import com.api.intrachat.models.general.Usuario;
import com.api.intrachat.repositories.campania.OperacionRepository;
import com.api.intrachat.repositories.campania.projections.OperacionProjection;
import com.api.intrachat.services.interfaces.campania.*;
import com.api.intrachat.services.interfaces.general.IUsuarioService;
import com.api.intrachat.utils.constants.GeneralConstants;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.exceptions.errors.ErrorException400;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import com.api.intrachat.utils.mappers.OperacionMapper;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OperacionService implements IOperacionService {

    private final OperacionRepository operacionRepository;

    private final ISedeService sedeService;
    private final ICampaniaService campaniaService;
    private final IEquipoService equipoService;
    private final IUsuarioService usuarioService;

    public OperacionService(OperacionRepository operacionRepository,
                            ISedeService sedeService,
                            ICampaniaService campaniaService,
                            @Lazy IEquipoService equipoService,
                            IUsuarioService usuarioService) {
        this.operacionRepository = operacionRepository;
        this.sedeService = sedeService;
        this.campaniaService = campaniaService;
        this.equipoService = equipoService;
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
    public List<Operacion> obtenerOperacionesPorSede(Long idSede) {
        return operacionRepository.findBySede(sedeService.obtenerSedePorID(idSede));
    }

    @Override
    public List<Operacion> obtenerOperacionesPorCampania(Long idCampania) {
        return operacionRepository.findByCampania(campaniaService.obtenerCampaniaPorID(idCampania));
    }

    @Override
    public PaginatedResponse<List<OperacionEspecialResponse>> obtenerOperacionesPaginado(int page, int size,
                                                                                         boolean estado, Long idCampania,
                                                                                         String filtro) {
        if (page < 1 || size < 1) {
            throw new ErrorException400(PaginatedConstants.ERROR_PAGINA_LONGITUD_INVALIDO);
        }

        // Si no existen los servicios lanzan una excepción
        if (idCampania != null) {
            Campania campania = campaniaService.obtenerCampaniaPorID(idCampania);
        }

        int mostrarActivas = estado ? 1 : 0;

        Pageable pageable = PageRequest.of(
                page - 1, size, Sort.by("campania").ascending());

        Page<OperacionProjection> listado = operacionRepository.buscarOperacionesPorCampaniaYEstado(
                idCampania,
                mostrarActivas,
                filtro,
                pageable
        );

        List<OperacionEspecialResponse> operaciones = listado.getContent()
                .stream()
                .map(OperacionMapper::operacionEspecialResponse)
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

        Campania campania = campaniaService.obtenerCampaniaPorID(operacionRequest.getIdCampania());
        Usuario jefeOperacion = usuarioService.obtenerUsuarioPorID(operacionRequest.getIdJefeOperacion());
        Sede sede = sedeService.obtenerSedePorID(operacionRequest.getIdSede());

        // No se incluye validaciones de activo, se asume que ingresan idsValidos y activos.
        // No se incluye validaciones logicas o de consistencia, se asume que las entidades
        // no tienen relacion con otras entidades al realizar esta operacion.

        Operacion operacion = new Operacion(
                null,
                sede,
                campania,
                jefeOperacion,
                LocalDateTime.now(),
                null
        );

        operacionRepository.save(operacion);

        return GeneralConstants.mensajeEntidadCreada("Operación");
    }

    @Override
    public String modificarOperacion(Long id, OperacionRequest operacionRequest) {
        Operacion operacionModificar = obtenerOperacionPorID(id);

        // Campania
        if (operacionRequest.getIdCampania() != null) {
            Campania campania = campaniaService.obtenerCampaniaPorID(operacionRequest.getIdCampania());
            operacionModificar.setCampania(campania);
        }
        // Jefe de Operacion
        if (operacionRequest.getIdJefeOperacion() != null) {
            Usuario jefeOperacion = usuarioService.obtenerUsuarioPorID(operacionRequest.getIdJefeOperacion());
            operacionModificar.setJefeOperacion(jefeOperacion);
        }
        // Sede
        if (operacionRequest.getIdSede() != null) {
            Sede sede = sedeService.obtenerSedePorID(operacionRequest.getIdSede());
            operacionModificar.setSede(sede);
        }

        operacionRepository.save(operacionModificar);

        return GeneralConstants.mensajeEntidadActualizada("Operación");
    }

    @Transactional
    @Override
    public String finalizarOperacion(Long idOperacion) {

        Operacion operacion = obtenerOperacionPorID(idOperacion);
        operacion.setFechaFinalizacion(LocalDateTime.now());
        operacion = operacionRepository.save(operacion);

        List<Equipo> equiposDeOperacion = equipoService.obtenerEquiposPorOperacion(idOperacion);

        if (!equiposDeOperacion.isEmpty()) {
            equiposDeOperacion.forEach(val -> {
                if (val.getFechaCierre() == null)
                    equipoService.establecerEquipoComoInoperativo(val.getId());
            });
        }

        return "Finalización de operación registrada satisfactoriamente.";
    }

}
