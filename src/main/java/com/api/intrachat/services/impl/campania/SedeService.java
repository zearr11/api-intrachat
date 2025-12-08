package com.api.intrachat.services.impl.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.request.SedeRequest;
import com.api.intrachat.dto.request.SedeRequest2;
import com.api.intrachat.dto.response.SedeResponse;
import com.api.intrachat.models.campania.Sede;
import com.api.intrachat.repositories.campania.SedeRepository;
import com.api.intrachat.services.interfaces.campania.IOperacionService;
import com.api.intrachat.services.interfaces.campania.ISedeService;
import com.api.intrachat.utils.constants.GeneralConstants;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.exceptions.errors.ErrorException400;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import com.api.intrachat.utils.exceptions.errors.ErrorException409;
import com.api.intrachat.utils.mappers.SedeMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SedeService implements ISedeService {

    private final SedeRepository sedeRepository;

    private final IOperacionService operacionService;

    public SedeService(SedeRepository sedeRepository,
                       @Lazy IOperacionService operacionService) {
        this.sedeRepository = sedeRepository;
        this.operacionService = operacionService;
    }

    @Override
    public Sede obtenerSedePorID(Long id) {
        return sedeRepository.findById(id).orElseThrow(
                () -> new ErrorException404(
                        GeneralConstants.mensajeEntidadNoExiste("Sede", id.toString())
                )
        );
    }

    @Override
    public List<Sede> obtenerSedes() {
        return sedeRepository.findAll();
    }

    @Override
    public PaginatedResponse<List<SedeResponse>> obtenerSedesPaginado(int page, int size, boolean estado, String filtro) {

        if (page < 1 || size < 1) {
            throw new ErrorException400(PaginatedConstants.ERROR_PAGINA_LONGITUD_INVALIDO);
        }

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("nombre").ascending());
        Page<Sede> listado = sedeRepository.buscarPorFiltro(filtro, estado, pageable);

        List<SedeResponse> sedes = listado.getContent()
                .stream()
                .map(SedeMapper::sedeResponse)
                .toList();

        return new PaginatedResponse<>(
                page,
                size,
                sedes.size(),
                listado.getTotalElements(),
                listado.getTotalPages(),
                sedes
        );
    }

    @Override
    public String crearSede(SedeRequest sedeRequest) {

        if (sedeRepository.findByNombre(sedeRequest.getNombre()).isPresent())
            throw new ErrorException409(
                    GeneralConstants.mensajeEntidadYaRegistrada("El nombre de la sede ingresada")
            );

        Sede nuevaSede = new Sede(
                null,
                sedeRequest.getNombre(),
                sedeRequest.getDireccion(),
                sedeRequest.getNumeroPostal(),
                GeneralConstants.ESTADO_DEFAULT
        );

        sedeRepository.save(nuevaSede);

        return GeneralConstants.mensajeEntidadCreada("Sede");
    }

    @Override
    public String modificarSede(Long id, SedeRequest2 sedeRequest) {
        Sede sedeActualizar = obtenerSedePorID(id);

        // Nombre
        if (sedeRequest.getNombre() != null && !sedeRequest.getNombre().isBlank()) {
            Optional<Sede> sedeCoincidente = sedeRepository.findByNombre(sedeRequest.getNombre());

            if (sedeCoincidente.isPresent() && !id.equals(sedeCoincidente.get().getId()))
                throw new ErrorException409(
                        GeneralConstants.mensajeEntidadYaRegistrada("El nombre de la sede ingresada")
                );

            sedeActualizar.setNombre(sedeRequest.getNombre());
        }
        // Direccion
        if (sedeRequest.getDireccion() != null && !sedeRequest.getDireccion().isBlank()) {
            sedeActualizar.setDireccion(sedeRequest.getDireccion());
        }
        // Numero Postal
        if (sedeRequest.getNumeroPostal() != null) {
            sedeActualizar.setNumeroPostal(sedeRequest.getNumeroPostal());
        }
        // Estado
        if (sedeRequest.getEstado() != null) {
            if (sedeRequest.getEstado())
                sedeActualizar.setEstado(true);
            else {
                operacionService.obtenerOperacionesPorSede(sedeActualizar.getId())
                        .forEach(val -> {
                            boolean estadoOperacion = val.getFechaFinalizacion() == null;

                            if (estadoOperacion) throw new ErrorException409(
                                    "La sede cuenta con operaciones activas, no es posible deshabilitarlo."
                            );
                        });
                sedeActualizar.setEstado(false);
            }
        }

        sedeRepository.save(sedeActualizar);
        return GeneralConstants.mensajeEntidadCreada("Sede");
    }

}
