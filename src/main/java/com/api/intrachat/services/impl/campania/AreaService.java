package com.api.intrachat.services.impl.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.request.AreaRequest;
import com.api.intrachat.dto.response.AreaResponse;
import com.api.intrachat.models.campania.Area;
import com.api.intrachat.repositories.campania.AreaRepository;
import com.api.intrachat.services.interfaces.campania.IAreaService;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.constants.GeneralConstants;
import com.api.intrachat.utils.exceptions.errors.ErrorException400;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import com.api.intrachat.utils.exceptions.errors.ErrorException409;
import com.api.intrachat.utils.mappers.AreaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AreaService implements IAreaService {

    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @Override
    public Area obtenerAreaPorID(Long id) {
        return areaRepository.findById(id).orElseThrow(
                () -> new ErrorException404(
                        GeneralConstants.mensajeEntidadNoExiste("Área", id.toString())
                )
        );
    }

    @Override
    public List<Area> obtenerAreas() {
        return areaRepository.findAll();
    }

    @Override
    public PaginatedResponse<List<AreaResponse>> obtenerAreasPaginado(int page, int size) {

        if (page < 1 || size < 1) {
            throw new ErrorException400(PaginatedConstants.ERROR_PAGINA_LONGITUD_INVALIDO);
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Area> listado = areaRepository.findAll(pageable);

        List<AreaResponse> areas = listado.getContent()
                .stream()
                .map(AreaMapper::areaResponse)
                .toList();

        return new PaginatedResponse<>(
                page,
                size,
                areas.size(),
                listado.getTotalElements(),
                listado.getTotalPages(),
                areas
        );
    }

    @Override
    public String crearArea(AreaRequest areaRequest) {

        if (areaRepository.findByNombre(areaRequest.getNombre()).isPresent())
            throw new ErrorException409(GeneralConstants.mensajeEntidadYaRegistrada("Área"));

        Area nuevaArea = Area.builder()
                .nombre(areaRequest.getNombre())
                .build();
        areaRepository.save(nuevaArea);

        return GeneralConstants.mensajeEntidadCreada("Área");
    }

    @Override
    public String modificarArea(Long id, AreaRequest areaRequest) {

        Area areaActualizar = obtenerAreaPorID(id);

        // Nombre
        if (areaRequest.getNombre() != null && !areaRequest.getNombre().isBlank()) {
            Optional<Area> areaCoincidente = areaRepository.findByNombre(areaRequest.getNombre());

            if (areaCoincidente.isPresent() && !id.equals(areaCoincidente.get().getId()))
                throw new ErrorException409(GeneralConstants.mensajeEntidadYaRegistrada("Área"));

            areaActualizar.setNombre(areaRequest.getNombre());
        }

        areaRepository.save(areaActualizar);

        return GeneralConstants.mensajeEntidadActualizada("Área");
    }

}
