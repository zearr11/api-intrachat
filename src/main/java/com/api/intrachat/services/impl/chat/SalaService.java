package com.api.intrachat.services.impl.chat;

import com.api.intrachat.dto.request.IntegranteRequest;
import com.api.intrachat.dto.request.IntegranteRequest2;
import com.api.intrachat.dto.request.SalaRequest;
import com.api.intrachat.dto.request.SalaRequest2;
import com.api.intrachat.models.campania.EquipoUsuarios;
import com.api.intrachat.models.chat.Integrante;
import com.api.intrachat.models.chat.Sala;
import com.api.intrachat.models.general.Usuario;
import com.api.intrachat.repositories.chat.IntegranteRepository;
import com.api.intrachat.repositories.chat.SalaRepository;
import com.api.intrachat.services.interfaces.chat.ISalaService;
import com.api.intrachat.services.interfaces.general.IUsuarioService;
import com.api.intrachat.utils.constants.GeneralConstants;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalaService implements ISalaService  {

    private final SalaRepository salaRepository;
    private final IntegranteRepository integranteRepository;

    private final IUsuarioService usuarioService;

    public SalaService(SalaRepository salaRepository,
                       IntegranteRepository integranteRepository,
                       IUsuarioService usuarioService) {
        this.salaRepository = salaRepository;
        this.integranteRepository = integranteRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    public Sala obtenerSalaPorId(Long id) {
        return salaRepository.findById(id).orElseThrow(
                () -> new ErrorException404(
                        GeneralConstants.mensajeEntidadNoExiste("Sala", id.toString())
                )
        );
    }

    @Override
    public Sala obtenerSalaPrivadaPorIntegrantes(Long idUsuario1, Long idUsuario2) {
        Sala sala = salaRepository.obtenerSalaPrivadaPorIntegrantes(idUsuario1, idUsuario2);
        /*
        if (sala == null)
            throw new ErrorException404("No se encontró una sala que vincule a ambos usuarios.");
        */
        return sala;
    }

    @Override
    public List<Sala> obtenerSalasPorUsuario(Long idUsuario) {
        Usuario usuario = usuarioService.obtenerUsuarioPorID(idUsuario);

        return integranteRepository.findByUsuario(usuario)
                .stream()
                .filter(Integrante::getEstado)
                .map(Integrante::getSala)
                .toList();
    }

    @Override
    public List<Integrante> obtenerIntegrantesDeSala(Long idSala) {
        Sala sala = obtenerSalaPorId(idSala);
        return integranteRepository.findBySala(sala);
    }

    @Transactional
    @Override
    public Sala crearSala(SalaRequest salaRequest) {
        Sala nuevaSala = new Sala(null, salaRequest.getTipoSala(), LocalDateTime.now());
        nuevaSala = salaRepository.save(nuevaSala);

        for (IntegranteRequest integrante : salaRequest.getIntegrantes()) {
            Usuario usuarioIntegrante = usuarioService.obtenerUsuarioPorID(integrante.getIdUsuario());
            Integrante nuevoIntegrante = new Integrante(null,
                    integrante.getTipoPermiso(), GeneralConstants.ESTADO_DEFAULT,
                    usuarioIntegrante, nuevaSala
                    );
            integranteRepository.save(nuevoIntegrante);
        }

        return nuevaSala;
    }

    @Transactional
    @Override
    public Sala actualizarIntegrantesDeSalaGrupal(Long id, SalaRequest2 salaRequest) {

        Sala sala = obtenerSalaPorId(id);
        List<Integrante> integrantesActuales = obtenerIntegrantesDeSala(sala.getId());

        // AGRUPAR TODAS LAS FILAS DE CADA USUARIO
        Map<Long, List<Integrante>> mapaPorUsuario =
                integrantesActuales.stream()
                        .collect(Collectors.groupingBy(i -> i.getUsuario().getId()));

        // MAPA: usuario → su fila ACTIVA o la última registrada
        Map<Long, Integrante> mapaActuales = mapaPorUsuario.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            List<Integrante> lista = entry.getValue();

                            // priorizar activo
                            return lista.stream()
                                    .filter(Integrante::getEstado)
                                    .findFirst()
                                    // sino, tomar el último insertado
                                    .orElseGet(() ->
                                            lista.stream()
                                                    .max(Comparator.comparing(Integrante::getId))
                                                    .orElse(lista.getFirst())
                                    );
                        }
                ));

        // IDs enviados
        Set<Long> idsNuevos = salaRequest.getIntegrantes().stream()
                .map(IntegranteRequest2::getIdUsuario)
                .collect(Collectors.toSet());

        // 1. PROCESAR LOS QUE VIENEN EN EL REQUEST
        for (IntegranteRequest2 req : salaRequest.getIntegrantes()) {

            List<Integrante> historial = mapaPorUsuario.get(req.getIdUsuario());

            if (historial != null) {

                // DESACTIVAR TODAS LAS FILAS PREVIAS
                for (Integrante reg : historial) {
                    if (reg.getEstado()) {
                        reg.setEstado(false);
                        integranteRepository.save(reg);
                    }
                }

                // REACTIVAR ÚNICA FILA (NO CREAR OTRA)
                Integrante activo = historial.stream()
                        .max(Comparator.comparing(Integrante::getId))
                        .orElse(null);

                if (activo != null) {
                    activo.setEstado(req.getEstado());
                    activo.setPermiso(req.getTipoPermiso());
                    integranteRepository.save(activo);
                }

            } else {
                // NO EXISTE NINGÚN REGISTRO, CREAR UNO
                Integrante nuevo = new Integrante(
                        null,
                        req.getTipoPermiso(),
                        true,
                        usuarioService.obtenerUsuarioPorID(req.getIdUsuario()),
                        sala
                );
                integranteRepository.save(nuevo);
            }
        }

        // 2. DESACTIVAR TODOS LOS QUE YA NO ESTÁN EN EL REQUEST
        for (Map.Entry<Long, List<Integrante>> entry : mapaPorUsuario.entrySet()) {

            Long idUsuario = entry.getKey();

            if (!idsNuevos.contains(idUsuario)) {
                // DESACTIVAR TODOS LOS REGISTROS ACTIVOS DE ESE USUARIO
                for (Integrante reg : entry.getValue()) {
                    if (reg.getEstado()) {
                        reg.setEstado(false);
                        integranteRepository.save(reg);
                    }
                }
            }
        }

        return sala;
    }


}
