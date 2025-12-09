package com.api.intrachat.services.impl.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.request.*;
import com.api.intrachat.dto.response.EquipoEspecialResponse;
import com.api.intrachat.dto.response.EquipoResponse;
import com.api.intrachat.models.campania.EquipoUsuarios;
import com.api.intrachat.models.campania.Operacion;
import com.api.intrachat.models.chat.Grupo;
import com.api.intrachat.models.chat.Integrante;
import com.api.intrachat.models.chat.Sala;
import com.api.intrachat.models.general.Usuario;
import com.api.intrachat.repositories.campania.EquipoUsuariosRepository;
import com.api.intrachat.repositories.campania.projections.EquipoProjection;
import com.api.intrachat.repositories.chat.GrupoRepository;
import com.api.intrachat.repositories.chat.IntegranteRepository;
import com.api.intrachat.services.interfaces.campania.IOperacionService;
import com.api.intrachat.services.interfaces.chat.IGrupoService;
import com.api.intrachat.services.interfaces.chat.ISalaService;
import com.api.intrachat.services.interfaces.general.IUsuarioService;
import com.api.intrachat.utils.constants.GrupoConstants;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.enums.Permiso;
import com.api.intrachat.utils.enums.TipoSala;
import com.api.intrachat.utils.exceptions.errors.ErrorException400;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import com.api.intrachat.models.campania.Equipo;
import com.api.intrachat.repositories.campania.EquipoRepository;
import com.api.intrachat.services.interfaces.campania.IEquipoService;
import com.api.intrachat.utils.constants.GeneralConstants;
import com.api.intrachat.utils.helpers.UsuarioHelper;
import com.api.intrachat.utils.mappers.EquipoMapper;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EquipoService implements IEquipoService {
    private final IOperacionService operacionService;
    private final IGrupoService grupoService;
    private final IUsuarioService usuarioService;
    private final ISalaService salaService;

    private final EquipoUsuariosRepository equipoUsuariosRepository;
    private final EquipoRepository equipoRepository;
    private final GrupoRepository grupoRepository;
    private final IntegranteRepository integranteRepository;

    public EquipoService(@Lazy IOperacionService operacionService,
                         IGrupoService grupoService,
                         IUsuarioService usuarioService,
                         ISalaService salaService,
                         EquipoUsuariosRepository equipoUsuariosRepository,
                         EquipoRepository equipoRepository,
                         GrupoRepository grupoRepository,
                         IntegranteRepository integranteRepository) {
        this.operacionService = operacionService;
        this.grupoService = grupoService;
        this.usuarioService = usuarioService;
        this.salaService = salaService;
        this.equipoUsuariosRepository = equipoUsuariosRepository;
        this.equipoRepository = equipoRepository;
        this.grupoRepository = grupoRepository;
        this.integranteRepository = integranteRepository;
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
        return equipoRepository.findByGrupo(grupoService.obtenerGrupoPorId(idGrupo)).orElseThrow(
                () -> new ErrorException404(
                        GeneralConstants.MENSAJE_GENERICO_NO_EXISTE
                )
        );
    }

    @Override
    public EquipoResponse obtenerEquipoResponsePorId(Long id) {
        Equipo equipo = obtenerEquipoPorID(id);
        List<Integrante> integrantes = salaService.obtenerIntegrantesDeSala(equipo.getGrupo().getSala().getId());
        List<EquipoUsuarios> integrantesCompletos = equipoUsuariosRepository.findByEquipo(equipo);

        return EquipoMapper.equipoResponse(equipo, integrantes, integrantesCompletos);
    }

    @Override
    public List<Equipo> obtenerEquiposPorOperacion(Long idOperacion) {
        return equipoRepository.findByOperacion(operacionService.obtenerOperacionPorID(idOperacion));
    }

    @Override
    public List<Equipo> obtenerEquiposRegular() {
        return equipoRepository.findAll();
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
                .map(EquipoMapper::equipoEspecialResponse)
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

    @Transactional
    @Override
    public String crearEquipo(EquipoRequest equipoRequest) {
        Operacion operacion = operacionService.obtenerOperacionPorID(equipoRequest.getIdOperacion());
        Usuario supervisor = usuarioService.obtenerUsuarioPorID(equipoRequest.getIdSupervisor());

        equipoRequest.getIntegrantes().add(
                new IntegranteRequest(
                        Permiso.ADMINISTRADOR,
                        operacion.getJefeOperacion().getId()
                ));
        equipoRequest.getIntegrantes().add(
                new IntegranteRequest(
                        Permiso.ADMINISTRADOR,
                        supervisor.getId()
                ));

        Sala salaNueva = salaService.crearSala(
                new SalaRequest(TipoSala.GRUPO, equipoRequest.getIntegrantes()));
        Grupo grupoNuevo = grupoService.crearGrupo(
                new GrupoRequest(
                        GrupoConstants.DESCRIPCION_DEFAULT,
                        GrupoConstants.generarNombreGrupo(
                                operacion.getCampania(),
                                UsuarioHelper.obtenerPrimerApellido(supervisor.getPersona().getApellidos())
                        ),
                        salaNueva.getId()
                ));
        Equipo equipoNuevo = new Equipo(
                null,
                supervisor,
                grupoNuevo,
                operacion,
                LocalDateTime.now(),
                null
        );

        equipoRepository.save(equipoNuevo);

        equipoRequest.getIntegrantes().forEach(val -> {
            EquipoUsuarios nuevoIntegrante = new EquipoUsuarios(
                    null,
                    LocalDateTime.now(),
                    null,
                    val.getTipoPermiso(),
                    GeneralConstants.ESTADO_DEFAULT,
                    usuarioService.obtenerUsuarioPorID(val.getIdUsuario()),
                    equipoNuevo
            );
            equipoUsuariosRepository.save(nuevoIntegrante);
        });

        return GeneralConstants.mensajeEntidadCreada("Equipo");
    }

    @Transactional
    @Override
    public String modificarEquipo(Long id, EquipoRequest2 equipoRequest) {

        Equipo equipo = obtenerEquipoPorID(id);

        // ============================
        // 0. Crear COPIA del request
        // ============================
        List<IntegranteRequest> integrantesProcesados = new ArrayList<>(equipoRequest.getIntegrantes());

        // ============================
        // 1. Actualizar datos base
        // ============================
        if (equipoRequest.getIdOperacion() != null) {

            Operacion operacion = operacionService.obtenerOperacionPorID(equipoRequest.getIdOperacion());
            equipo.setOperacion(operacion);

            // agregar jefe
            integrantesProcesados.add(
                    new IntegranteRequest(Permiso.ADMINISTRADOR, operacion.getJefeOperacion().getId())
            );
        }

        if (equipoRequest.getIdSupervisor() != null) {

            Usuario supervisor = usuarioService.obtenerUsuarioPorID(equipoRequest.getIdSupervisor());
            equipo.setSupervisor(supervisor);

            // agregar supervisor
            integrantesProcesados.add(
                    new IntegranteRequest(Permiso.ADMINISTRADOR, supervisor.getId())
            );
        }

        if (equipoRequest.getNombre() != null && !equipoRequest.getNombre().isBlank()) {
            equipo.getGrupo().setNombre(equipoRequest.getNombre());
        }

        if (equipoRequest.getDescripcion() != null && !equipoRequest.getDescripcion().isBlank()) {
            equipo.getGrupo().setDescripcion(equipoRequest.getDescripcion());
        }

        equipoRepository.save(equipo);

        // ============================
        // 2. Usuarios actuales
        // ============================
        List<EquipoUsuarios> usuariosActuales = equipoUsuariosRepository.findByEquipo(equipo);

        Map<Long, EquipoUsuarios> mapaActuales = usuariosActuales.stream()
                .collect(Collectors.groupingBy(eu -> eu.getUsuario().getId()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            List<EquipoUsuarios> lista = entry.getValue();

                            return lista.stream()
                                    .filter(EquipoUsuarios::getEstado)
                                    .findFirst()
                                    .orElseGet(() ->
                                            lista.stream()
                                                    .max(Comparator.comparing(EquipoUsuarios::getFechaInicio))
                                                    .orElse(lista.getFirst())
                                    );
                        }
                ));

        // IDs nuevos EN LA COPIA (no el request original)
        Set<Long> idsNuevos = integrantesProcesados.stream()
                .map(IntegranteRequest::getIdUsuario)
                .collect(Collectors.toSet());

        // ============================
        // 3. Procesar integrantes
        // ============================
        for (IntegranteRequest integrante : integrantesProcesados) {

            EquipoUsuarios existente = mapaActuales.get(integrante.getIdUsuario());

            if (existente != null) {

                if (!existente.getEstado()) {

                    // CREA nueva instancia activa
                    EquipoUsuarios nuevo = new EquipoUsuarios(
                            null,
                            LocalDateTime.now(),
                            null,
                            integrante.getTipoPermiso(),
                            true,
                            usuarioService.obtenerUsuarioPorID(integrante.getIdUsuario()),
                            equipo
                    );
                    equipoUsuariosRepository.save(nuevo);

                } else {

                    // actualizar permiso
                    if (integrante.getTipoPermiso() != null &&
                            !existente.getPermiso().equals(integrante.getTipoPermiso())) {

                        existente.setPermiso(integrante.getTipoPermiso());
                        equipoUsuariosRepository.save(existente);
                    }
                }

            } else {

                // crear nuevo usuario en equipo
                EquipoUsuarios nuevo = new EquipoUsuarios(
                        null,
                        LocalDateTime.now(),
                        null,
                        integrante.getTipoPermiso(),
                        true,
                        usuarioService.obtenerUsuarioPorID(integrante.getIdUsuario()),
                        equipo
                );
                equipoUsuariosRepository.save(nuevo);
            }
        }

        // ============================
        // 4. DESACTIVAR usuarios que ya no están
        // ============================
        for (EquipoUsuarios actual : usuariosActuales) {

            if (!idsNuevos.contains(actual.getUsuario().getId()) && actual.getEstado()) {

                actual.setEstado(false);
                actual.setFechaFin(LocalDateTime.now());
                equipoUsuariosRepository.save(actual);
            }
        }

        // ============================
        // 5. Actualizar sala
        // ============================
        List<EquipoUsuarios> usuariosActualizados =
                equipoUsuariosRepository.findByEquipo(equipo);

        List<IntegranteRequest2> integrantesActualizados =
                usuariosActualizados.stream()
                        .map(val -> new IntegranteRequest2(
                                val.getEstado(),
                                val.getPermiso(),
                                val.getUsuario().getId()
                        )).toList();

        integrantesActualizados.forEach(val -> {
            System.out.println(val.getIdUsuario() + " - " + val.getTipoPermiso().toString() + " - " + val.getEstado());
        });

        salaService.actualizarIntegrantesDeSalaGrupal(
                equipo.getGrupo().getSala().getId(),
                new SalaRequest2(integrantesActualizados)
        );

        return GeneralConstants.mensajeEntidadActualizada("Equipo");
    }


    @Override
    public String establecerEquipoComoInoperativo(Long idEquipo) {

        // Desactivar equipo
        Equipo equipo = obtenerEquipoPorID(idEquipo);
        equipo.setFechaCierre(LocalDateTime.now());
        equipoRepository.save(equipo);

        // Desactivar equipoUsuarios
        List<EquipoUsuarios> equipoUsuarios = equipoUsuariosRepository.findByEquipo(equipo);
        equipoUsuarios.forEach(eu -> {
            eu.setEstado(false);
            eu.setFechaFin(LocalDateTime.now());
        });
        equipoUsuariosRepository.saveAll(equipoUsuarios);

        // Desactivar integrantes
        Grupo grupo = equipo.getGrupo();
        Sala sala = grupo.getSala();
        List<Integrante> integrantes = salaService.obtenerIntegrantesDeSala(sala.getId());
        integrantes.forEach(i -> i.setEstado(false));
        integranteRepository.saveAll(integrantes);

        // Desactivar grupo
        grupo.setEstado(false);
        grupo.setUltimaModificacion(LocalDateTime.now());
        grupoRepository.save(grupo);

        return "Cierre de equipo realizado satisfactoriamente.";
    }

}
