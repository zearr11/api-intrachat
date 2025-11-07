package com.api.intrachat.services.impl.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.request.EquipoRequest;
import com.api.intrachat.dto.response.EquipoResponse;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import com.api.intrachat.models.campania.Equipo;
import com.api.intrachat.repositories.campania.EquipoRepository;
import com.api.intrachat.services.interfaces.campania.IEquipoService;
import com.api.intrachat.utils.constants.GeneralConstants;
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
    public PaginatedResponse<List<EquipoResponse>> obtenerEquiposPaginado(int page, int size, int numeroEquipo,
                                                                          Long idGrupo, Long idCampania, Long idSede,
                                                                          Long idUsuario) {
        return null;
    }

    @Override
    public String crearEquipo(EquipoRequest equipoRequest) {
        return "";
    }

    @Override
    public String finalizarRealacionDeUsuarioConEquipo(Long idEquipo, Long idUsuario) {
        return "";
    }

}
