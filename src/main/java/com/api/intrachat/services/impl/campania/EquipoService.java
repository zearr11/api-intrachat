package com.api.intrachat.services.impl.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.request.EquipoRequest;
import com.api.intrachat.dto.response.EquipoEspecialResponse;
import com.api.intrachat.dto.response.UsuarioEquipoResponse;
import com.api.intrachat.repositories.campania.projections.EquipoProjection;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.exceptions.errors.ErrorException400;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import com.api.intrachat.models.campania.Equipo;
import com.api.intrachat.repositories.campania.EquipoRepository;
import com.api.intrachat.services.interfaces.campania.IEquipoService;
import com.api.intrachat.utils.constants.GeneralConstants;
import com.api.intrachat.utils.mappers.EquipoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EquipoService implements IEquipoService {

    private final EquipoRepository equipoRepository;

    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    @Override
    public Equipo obtenerEquipoPorID(Long id) {
        return equipoRepository.findById(id).orElseThrow(
                () -> new ErrorException404(
                        GeneralConstants.mensajeEntidadNoExiste("Equipo", id.toString())
                )
        );
    }

    @Override
    public Equipo obtenerEquipoPorGrupo(Long idGrupo) {
        return null;
    }

    @Override
    public PaginatedResponse<List<UsuarioEquipoResponse>> obtenerUsuariosEquipoPaginado(int page, int size,
                                                                                        boolean estado, Long idCampania,
                                                                                        Long idOperacion, Long idEquipo) {
        if (page < 1 || size < 1) {
            throw new ErrorException400(PaginatedConstants.ERROR_PAGINA_LONGITUD_INVALIDO);
        }

        return null;
    }

    @Override
    public PaginatedResponse<List<EquipoEspecialResponse>> obtenerEquiposPaginado(int page, int size,
                                                                                  boolean estado, String filtro) {
        if (page < 1 || size < 1) {
            throw new ErrorException400(PaginatedConstants.ERROR_PAGINA_LONGITUD_INVALIDO);
        }

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("sede").ascending());
        Page<EquipoProjection> listado = equipoRepository.buscarEquiposConFiltro(filtro, estado, pageable);

        List<EquipoEspecialResponse> equipos = listado.getContent()
                .stream()
                .map(EquipoMapper::equipoResponse)
                .toList();

        return new PaginatedResponse<>(
                page,
                size,
                equipos.size(),
                listado.getTotalElements(),
                listado.getTotalPages(),
                equipos
        );
    }

    @Override
    public String crearEquipo(EquipoRequest equipoRequest) {
        return "";
    }

    @Override
    public String deshabilitarEquipo(Long idEquipo) {
        return "";
    }

    @Override
    public String agregarUsuarioAEquipo(Long idEquipo, Long idUsuario) {
        return "";
    }

    @Override
    public String cambiarPermisoDeUsuarioEnEquipo(Long idEquipo, Long idUsuario) {
        return "";
    }

    @Override
    public String finalizarRelacionDeUsuarioConEquipo(Long idEquipo, Long idUsuario) {
        return "";
    }

}
