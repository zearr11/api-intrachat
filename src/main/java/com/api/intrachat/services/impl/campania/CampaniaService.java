package com.api.intrachat.services.impl.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.request.CampaniaRequest;
import com.api.intrachat.dto.request.CampaniaRequest2;
import com.api.intrachat.dto.response.CampaniaResponse;
import com.api.intrachat.models.campania.Campania;
import com.api.intrachat.repositories.campania.CampaniaRepository;
import com.api.intrachat.services.interfaces.campania.ICampaniaService;
import com.api.intrachat.services.interfaces.campania.IEmpresaService;
import com.api.intrachat.utils.constants.CampaniaConstants;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.constants.GeneralConstants;
import com.api.intrachat.utils.exceptions.errors.ErrorException400;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import com.api.intrachat.utils.exceptions.errors.ErrorException409;
import com.api.intrachat.utils.mappers.CampaniaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CampaniaService implements ICampaniaService {

    private final CampaniaRepository campaniaRepository;

    private final IEmpresaService empresaService;

    public CampaniaService(CampaniaRepository campaniaRepository,
                           IEmpresaService empresaService) {
        this.campaniaRepository = campaniaRepository;
        this.empresaService = empresaService;
    }

    @Override
    public Campania obtenerCampaniaPorID(Long id) {
        return campaniaRepository.findById(id).orElseThrow(
                () -> new ErrorException404(
                        GeneralConstants.mensajeEntidadNoExiste("Campaña", id.toString())
                ));
    }

    @Override
    public Campania obtenerCampaniaPorNombre(String nombre) {
        return campaniaRepository.findByNombre(nombre).orElseThrow(
                () -> new ErrorException404(
                        CampaniaConstants.mensajeNombreNoExiste(nombre)
                ));
    }

    @Override
    public List<Campania> obtenerCampaniasPorEmpresa(Long idEmpresa) {
        return campaniaRepository.findByEmpresa(
                empresaService.obtenerEmpresaPorID(idEmpresa)
        );
    }

    @Override
    public PaginatedResponse<List<CampaniaResponse>> obtenerCampaniasPaginado(int page, int size,
                                                                              boolean estado, String filtro) {
        if (page < 1 || size < 1) {
            throw new ErrorException400(PaginatedConstants.ERROR_PAGINA_LONGITUD_INVALIDO);
        }

        /*

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("nombre").ascending());
        Page<Campania> listado = campaniaRepository.buscarPorFiltro(estado, filtro, pageable);

        List<CampaniaResponse> campanias = listado.getContent()
                .stream()
                .map(CampaniaMapper::campaniaResponse)
                .toList();

        return new PaginatedResponse<>(
                page,
                size,
                campanias.size(),
                listado.getTotalElements(),
                listado.getTotalPages(),
                campanias
        );


        */

        return null;
    }

    @Override
    public String crearCampania(CampaniaRequest campaniaRequest) {

        Optional<Campania> campaniaCoincidente = campaniaRepository.findByNombre(campaniaRequest.getNombre());
        if (campaniaCoincidente.isPresent())
            throw new ErrorException409(CampaniaConstants.ERROR_NOMBRE_REGISTRADO);

        final LocalDateTime fechaHoy = LocalDateTime.now();

        Campania nuevaCampania = Campania.builder()
                .nombre(campaniaRequest.getNombre())
                .empresa(empresaService.obtenerEmpresaPorID(campaniaRequest.getIdEmpresa()))
                .areaAtencion(campaniaRequest.getAreaAtencion())
                .medioComunicacion(campaniaRequest.getMedioComunicacion())
                .estado(GeneralConstants.ESTADO_DEFAULT)
                .fechaCreacion(fechaHoy)
                .ultimaModificacion(fechaHoy)
                .build();

        campaniaRepository.save(nuevaCampania);

        return GeneralConstants.mensajeEntidadCreada("Campaña");
    }

    @Override
    public String modificarCampania(Long id, CampaniaRequest2 campaniaRequest) {

        // Declaracion fecha actual
        final LocalDateTime fechaHoy = LocalDateTime.now();

        // Busqueda de entidad
        Campania campaniaModificar = obtenerCampaniaPorID(id);

        // Nombre
        if (campaniaRequest.getNombre() != null && !campaniaRequest.getNombre().isBlank()) {
            campaniaModificar.setNombre(campaniaRequest.getNombre());
        }
        // Empresa
        if (campaniaRequest.getIdEmpresa() != null) {
            campaniaModificar.setEmpresa(empresaService.obtenerEmpresaPorID(campaniaRequest.getIdEmpresa()));
        }
        // Area
        if (campaniaRequest.getAreaAtencion() != null) {
            campaniaModificar.setAreaAtencion(campaniaRequest.getAreaAtencion());
        }
        // Medio de comunicación
        if (campaniaRequest.getMedioComunicacion() != null) {
            campaniaModificar.setMedioComunicacion(campaniaRequest.getMedioComunicacion());
        }
        // Estado
        if (campaniaRequest.getEstado() != null) {
            campaniaModificar.setEstado(campaniaRequest.getEstado());
        }

        // Actualizar ultima modificación
        campaniaModificar.setUltimaModificacion(fechaHoy);

        // Validación: El nombre es unique
        Optional<Campania> campaniaCoincidente = campaniaRepository.findByNombre(campaniaRequest.getNombre());
        if (campaniaCoincidente.isPresent()
                && !campaniaCoincidente.get().getId().equals(campaniaModificar.getId()))
            throw new ErrorException409(CampaniaConstants.ERROR_NOMBRE_REGISTRADO);

        return GeneralConstants.mensajeEntidadActualizada("Campaña");
    }

}
