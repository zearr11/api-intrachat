package com.api.intrachat.services.impl.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.response.SedeResponse;
import com.api.intrachat.models.campania.Sede;
import com.api.intrachat.repositories.campania.SedeRepository;
import com.api.intrachat.services.interfaces.campania.ISedeService;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.exceptions.errors.ErrorException400;
import com.api.intrachat.utils.mappers.SedeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SedeService implements ISedeService {

    private final SedeRepository sedeRepository;

    public SedeService(SedeRepository sedeRepository) {
        this.sedeRepository = sedeRepository;
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

}
