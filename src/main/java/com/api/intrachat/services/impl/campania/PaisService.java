package com.api.intrachat.services.impl.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.response.PaisResponse;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.exceptions.errors.ErrorException400;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import com.api.intrachat.utils.exceptions.errors.ErrorException409;
import com.api.intrachat.models.campania.Pais;
import com.api.intrachat.repositories.campania.PaisRepository;
import com.api.intrachat.services.interfaces.campania.IPaisService;
import com.api.intrachat.utils.constants.GeneralConstants;
import com.api.intrachat.dto.request.PaisRequest;
import com.api.intrachat.utils.mappers.PaisMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PaisService implements IPaisService {

    private final PaisRepository paisRepository;

    public PaisService(PaisRepository paisRepository) {
        this.paisRepository = paisRepository;
    }

    @Override
    public Pais obtenerPaisPorID(Long id) {
        return paisRepository.findById(id).orElseThrow(
                () -> new ErrorException404(
                        GeneralConstants.mensajeEntidadNoExiste("País", id.toString())
                )
        );
    }

    @Override
    public List<Pais> obtenerPaises() {
        return paisRepository.findAll();
    }

    @Override
    public PaginatedResponse<List<PaisResponse>> obtenerPaisesPaginado(int page, int size) {
        if (page < 1 || size < 1) {
            throw new ErrorException400(PaginatedConstants.ERROR_PAGINA_LONGITUD_INVALIDO);
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Pais> listado = paisRepository.findAll(pageable);

        List<PaisResponse> paises = listado.getContent()
                .stream()
                .map(PaisMapper::paisResponse)
                .toList();

        return new PaginatedResponse<>(
                page,
                size,
                paises.size(),
                listado.getTotalElements(),
                listado.getTotalPages(),
                paises
        );
    }

    @Override
    public String crearPais(PaisRequest paisRequest) {

        if (paisRepository.findByNombre(paisRequest.getNombre()).isPresent())
            throw new ErrorException409(GeneralConstants.mensajeEntidadYaRegistrada("País"));

        Pais nuevoPais = Pais.builder()
                .nombre(paisRequest.getNombre())
                .build();
        paisRepository.save(nuevoPais);

        return GeneralConstants.mensajeEntidadCreada("País");
    }

    @Override
    public String modificarPais(Long id, PaisRequest paisRequest) {

        Pais paisActualizar = obtenerPaisPorID(id);

        // Nombre
        if (paisRequest.getNombre() != null && !paisRequest.getNombre().isBlank()) {
            Optional<Pais> paisCoincidente = paisRepository.findByNombre(paisRequest.getNombre());

            if (paisCoincidente.isPresent() && !id.equals(paisCoincidente.get().getId()))
                throw new ErrorException409(GeneralConstants.mensajeEntidadYaRegistrada("País"));

            paisActualizar.setNombre(paisRequest.getNombre());
        }

        paisRepository.save(paisActualizar);

        return GeneralConstants.mensajeEntidadActualizada("País");
    }

}
