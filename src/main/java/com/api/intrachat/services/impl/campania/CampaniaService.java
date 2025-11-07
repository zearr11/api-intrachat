package com.api.intrachat.services.impl.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.request.CampaniaRequest;
import com.api.intrachat.dto.request.CampaniaRequest2;
import com.api.intrachat.dto.response.CampaniaResponse;
import com.api.intrachat.models.campania.Campania;
import com.api.intrachat.models.campania.Sede;
import com.api.intrachat.repositories.campania.CampaniaRepository;
import com.api.intrachat.services.interfaces.campania.IAreaService;
import com.api.intrachat.services.interfaces.campania.ICampaniaService;
import com.api.intrachat.services.interfaces.campania.IEmpresaService;
import com.api.intrachat.services.interfaces.campania.ISedeService;
import com.api.intrachat.utils.constants.CampaniaConstants;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.constants.GeneralConstants;
import com.api.intrachat.utils.exceptions.errors.ErrorException400;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import com.api.intrachat.utils.exceptions.errors.ErrorException409;
import com.api.intrachat.utils.mappers.CampaniaMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CampaniaService implements ICampaniaService {

    private final CampaniaRepository campaniaRepository;

    private final IEmpresaService empresaService;
    private final ISedeService sedeService;
    private final IAreaService areaService;

    public CampaniaService(CampaniaRepository campaniaRepository,
                           IEmpresaService empresaService,
                           ISedeService sedeService,
                           IAreaService areaService) {
        this.campaniaRepository = campaniaRepository;
        this.empresaService = empresaService;
        this.sedeService = sedeService;
        this.areaService = areaService;
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
    public List<Campania> obtenerCampaniasPorSede(Long idSede) {
        return campaniaRepository.findBySedeId(idSede);
    }

    @Override
    public PaginatedResponse<List<CampaniaResponse>> obtenerCampaniasPaginado(int page, int size, boolean estado) {

        if (page < 1 || size < 1) {
            throw new ErrorException400(PaginatedConstants.ERROR_PAGINA_LONGITUD_INVALIDO);
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Campania> listado = campaniaRepository.findByEstado(estado, pageable);

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
    }

    @Transactional
    @Override
    public String agregarSedeACampania(Long idCampania, Long idSede) {

        final LocalDateTime fechaHoy = LocalDateTime.now();

        // Busqueda de entidades
        Campania campania = obtenerCampaniaPorID(idCampania);
        Sede sede = sedeService.obtenerSedePorID(idSede);

        // Validacion: Si la sede ya esta registrada lanzar error
        for (Sede sedeIterador : campania.getCampaniaSedes()) {
            if (sedeIterador.getId().equals(sede.getId()))
                throw new ErrorException409("La campaña ya tiene registrada la sede indicada.");
        }

        campania.setUltimaModificacion(fechaHoy);

        // Agregar sede a campaña y guardar en bd
        campania.addCampaniaSedes(sede);
        campaniaRepository.save(campania);

        return GeneralConstants.mensajeEntidadAgregada("Sede");
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
                .area(areaService.obtenerAreaPorID(campaniaRequest.getIdEmpresa()))
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
        if (campaniaRequest.getIdArea() != null) {
            campaniaModificar.setArea(areaService.obtenerAreaPorID(campaniaRequest.getIdArea()));
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
