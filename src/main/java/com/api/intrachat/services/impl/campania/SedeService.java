package com.api.intrachat.services.impl.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.request.SedeRequest;
import com.api.intrachat.dto.response.SedeResponse;
import com.api.intrachat.models.campania.Pais;
import com.api.intrachat.models.campania.Sede;
import com.api.intrachat.repositories.campania.SedeRepository;
import com.api.intrachat.services.interfaces.campania.IPaisService;
import com.api.intrachat.services.interfaces.campania.ISedeService;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.constants.PaisConstants;
import com.api.intrachat.utils.constants.ResponseConstants;
import com.api.intrachat.utils.constants.SedeConstants;
import com.api.intrachat.utils.exceptions.errors.ErrorException400;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import com.api.intrachat.utils.exceptions.errors.ErrorException409;
import com.api.intrachat.utils.mappers.SedeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SedeService implements ISedeService {

    private final SedeRepository sedeRepository;

    private final IPaisService paisService;

    public SedeService(SedeRepository sedeRepository,
                       IPaisService paisService) {
        this.sedeRepository = sedeRepository;
        this.paisService = paisService;
    }

    @Override
    public Sede obtenerSedePorID(Long id) {
        return sedeRepository.findById(id).orElseThrow(
                () -> new ErrorException404(
                        ResponseConstants.mensajeEntidadNoExiste("Sede", id.toString())
                )
        );
    }

    @Override
    public List<Sede> obtenerSedes() {
        return sedeRepository.findAll();
    }

    @Override
    public List<Sede> obtenerSedesPorPais(Long idPais) {
        Pais paisBusqueda = paisService.obtenerPaisPorID(idPais);
        return sedeRepository.findByPais(paisBusqueda).orElseThrow(
                () -> new ErrorException404(PaisConstants.ERROR_PAIS_SIN_SEDES)
        );
    }

    @Override
    public PaginatedResponse<List<SedeResponse>> obtenerSedesPaginado(int page, int size) {
        if (page < 1 || size < 1) {
            throw new ErrorException400(PaginatedConstants.ERROR_PAGINA_LONGITUD_INVALIDO);
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Sede> listado = sedeRepository.findAll(pageable);

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
            throw new ErrorException409(SedeConstants.ERROR_NOMBRE_SEDE_REGISTRADO);

        Pais paisSeleccionado = paisService.obtenerPaisPorID(sedeRequest.getIdPais());

        Sede nuevaSede = Sede.builder()
                .nombre(sedeRequest.getNombre())
                .direccion(sedeRequest.getDireccion())
                .ciudad(sedeRequest.getCiudad())
                .pais(paisSeleccionado)
                .build();

        sedeRepository.save(nuevaSede);

        return ResponseConstants.mensajeEntidadCreada("Sede");
    }

    @Override
    public String modificarSede(Long id, SedeRequest sedeRequest) {

        Sede sedeActualizar = obtenerSedePorID(id);

        // Nombre
        if (sedeRequest.getNombre() != null && !sedeRequest.getNombre().isBlank()) {
            Optional<Sede> sedeCoincidente = sedeRepository.findByNombre(sedeRequest.getNombre());

            if (sedeCoincidente.isPresent() && !id.equals(sedeCoincidente.get().getId()))
                throw new ErrorException409(SedeConstants.ERROR_NOMBRE_SEDE_REGISTRADO);

            sedeActualizar.setNombre(sedeRequest.getNombre());
        }
        // Direccion
        if (sedeRequest.getDireccion() != null && !sedeRequest.getDireccion().isBlank())
            sedeActualizar.setDireccion(sedeRequest.getDireccion());
        // Ciudad
        if (sedeRequest.getCiudad() != null && !sedeRequest.getCiudad().isBlank())
            sedeActualizar.setCiudad(sedeRequest.getCiudad());
        // Pais
        if (sedeRequest.getIdPais() != null) {
            Pais paisSeleccionado = paisService.obtenerPaisPorID(sedeRequest.getIdPais());
            sedeActualizar.setPais(paisSeleccionado);
        }

        sedeRepository.save(sedeActualizar);

        return ResponseConstants.mensajeEntidadActualizada("Sede");
    }
}
