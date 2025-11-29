package com.api.intrachat.services.impl.chat;

import com.api.intrachat.dto.request.IntegranteRequest;
import com.api.intrachat.dto.request.IntegranteRequest2;
import com.api.intrachat.dto.request.SalaRequest;
import com.api.intrachat.dto.request.SalaRequest2;
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
import java.util.List;
import java.util.Map;
import java.util.Set;
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

        // Convertimos a mapa para accesos rápidos
        Map<Long, Integrante> mapaActuales = integrantesActuales.stream()
                .collect(Collectors.toMap(i -> i.getUsuario().getId(), i -> i));

        // IDs enviados en el request
        Set<Long> idsNuevos = salaRequest.getIntegrantes().stream()
                .map(IntegranteRequest2::getIdUsuario)
                .collect(Collectors.toSet());

        // 1. Procesar integrantes del request
        for (IntegranteRequest2 req : salaRequest.getIntegrantes()) {

            Integrante existente = mapaActuales.get(req.getIdUsuario());

            if (existente != null) {
                // Actualizar
                if (req.getEstado() != null) existente.setEstado(req.getEstado());
                if (req.getTipoPermiso() != null) existente.setPermiso(req.getTipoPermiso());
                integranteRepository.save(existente);
            } else {
                // Crear nuevo integrante
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

        // 2. Desactivar los que ya no están en el request
        for (Integrante actual : integrantesActuales) {
            if (!idsNuevos.contains(actual.getUsuario().getId())) {
                actual.setEstado(false);
                integranteRepository.save(actual);
            }
        }

        return sala;
    }

}
