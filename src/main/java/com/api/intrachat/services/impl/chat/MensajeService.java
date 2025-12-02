package com.api.intrachat.services.impl.chat;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.response.MensajeResponse;
import com.api.intrachat.models.chat.Fichero;
import com.api.intrachat.models.chat.Mensaje;
import com.api.intrachat.models.chat.Sala;
import com.api.intrachat.models.chat.Texto;
import com.api.intrachat.repositories.chat.FicheroRepository;
import com.api.intrachat.repositories.chat.MensajeRepository;
import com.api.intrachat.repositories.chat.TextoRepository;
import com.api.intrachat.services.interfaces.chat.IMensajeService;
import com.api.intrachat.services.interfaces.chat.ISalaService;
import com.api.intrachat.services.interfaces.general.IUsuarioService;
import com.api.intrachat.utils.constants.GeneralConstants;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.exceptions.errors.ErrorException400;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import com.api.intrachat.utils.mappers.MensajeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MensajeService implements IMensajeService {

    private final MensajeRepository mensajeRepository;
    private final TextoRepository textoRepository;
    private final FicheroRepository ficheroRepository;
    private final IUsuarioService usuarioService;
    private final ISalaService salaService;

    public MensajeService(MensajeRepository mensajeRepository,
                          TextoRepository textoRepository,
                          FicheroRepository ficheroRepository,
                          IUsuarioService usuarioService,
                          ISalaService salaService) {
        this.mensajeRepository = mensajeRepository;
        this.textoRepository = textoRepository;
        this.ficheroRepository = ficheroRepository;
        this.usuarioService = usuarioService;
        this.salaService = salaService;
    }

    @Override
    public Mensaje obtenerMensajePorId(Long idMensaje) {
        return mensajeRepository.findById(idMensaje).orElseThrow(
                () -> new ErrorException404(GeneralConstants.mensajeEntidadNoExiste(
                        "Mensaje", idMensaje.toString()
                ))
        );
    }

    @Override
    public Mensaje obtenerUltimoMensajeDeSala(Long idSala) {
        return mensajeRepository.findFirstBySalaIdOrderByFechaCreacionDesc(idSala)
                .orElse(null);
    }

    @Override
    public PaginatedResponse<List<MensajeResponse>> obtenerMensajesPorSala(int page, int size,
                                                                           String filtro, Long idSala) {
        if (page < 1 || size < 1) {
            throw new ErrorException400(PaginatedConstants.ERROR_PAGINA_LONGITUD_INVALIDO);
        }

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Mensaje> listado = mensajeRepository.obtenerMensajesDeSala(idSala, filtro, pageable);

        List<MensajeResponse> mensajes = listado.getContent()
                .stream()
                .map(mensaje -> {
                    Fichero fichero = obtenerFicheroDeMensaje(mensaje.getId());
                    Texto texto = obtenerTextoDeMensaje(mensaje.getId());
                    return MensajeMapper.mensajeResponse(
                            mensaje,
                            fichero == null ? null : fichero.getArchivo(),
                            texto
                    );
                })
                .toList();

        return new PaginatedResponse<>(
                page,
                size,
                mensajes.size(),
                listado.getTotalElements(),
                listado.getTotalPages(),
                mensajes
        );
    }

    @Override
    public Texto obtenerTextoDeMensaje(Long idMensaje) {
        return textoRepository.findByMensaje(obtenerMensajePorId(idMensaje));
    }

    @Override
    public Fichero obtenerFicheroDeMensaje(Long idMensaje) {
        return ficheroRepository.findByMensaje(obtenerMensajePorId(idMensaje)).orElse(null);
    }

    @Override
    public boolean mensajeEsDeChatPrivado(Long idMensaje, Long idUsuarioDestino) {
        Long idUsuarioLogeado = usuarioService.obtenerUsuarioActual().getId();

        if (idUsuarioDestino.equals(idUsuarioLogeado)) return false;

        // Obtener el mensaje
        Mensaje mensaje = mensajeRepository.findById(idMensaje)
                .orElse(null);

        if (mensaje == null) return false;

        Long idSalaMensaje = mensaje.getSala().getId();

        // Obtener la sala privada entre ambos usuarios
        Sala salaPrivada = salaService.obtenerSalaPrivadaPorIntegrantes(idUsuarioDestino, idUsuarioLogeado);

        if (salaPrivada == null) return false;

        return idSalaMensaje.equals(salaPrivada.getId());
    }

    @Override
    public boolean mensajeEsDeChatGrupal(Long idMensaje, Long idSala) {
        Mensaje mensaje = mensajeRepository.findById(idMensaje).orElse(null);

        if (mensaje == null) return false;

        return mensaje.getSala().getId().equals(idSala);
    }
}
