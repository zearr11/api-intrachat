package com.api.intrachat.services.impl.chat;

import com.api.intrachat.dto.request.IntegranteRequest;
import com.api.intrachat.dto.request.SalaRequest;
import com.api.intrachat.dto.request.customized.ChatRequest;
import com.api.intrachat.dto.response.GrupoResponse;
import com.api.intrachat.dto.response.MensajeResponse;
import com.api.intrachat.dto.response.SalaResponse;
import com.api.intrachat.dto.response.customized.contact.ContactoResponse;
import com.api.intrachat.dto.response.customized.contact.DatosGrupoResponse;
import com.api.intrachat.dto.response.customized.contact.DatosMensajeResponse;
import com.api.intrachat.dto.response.customized.contact.DatosUsuarioResponse;
import com.api.intrachat.dto.response.customized.current_chat.ChatResponse;
import com.api.intrachat.models.campania.Campania;
import com.api.intrachat.models.campania.EquipoUsuarios;
import com.api.intrachat.models.chat.*;
import com.api.intrachat.models.general.Usuario;
import com.api.intrachat.repositories.campania.EquipoUsuariosRepository;
import com.api.intrachat.repositories.chat.MensajeRepository;
import com.api.intrachat.repositories.chat.TextoRepository;
import com.api.intrachat.services.interfaces.chat.IGrupoService;
import com.api.intrachat.services.interfaces.chat.IMensajeService;
import com.api.intrachat.services.interfaces.chat.ISalaService;
import com.api.intrachat.services.interfaces.general.IUsuarioService;
import com.api.intrachat.utils.enums.Permiso;
import com.api.intrachat.utils.enums.TipoSala;
import com.api.intrachat.utils.helpers.UsuarioHelper;
import com.api.intrachat.utils.mappers.UsuarioMapper;
import jakarta.transaction.Transactional;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    private final MensajeRepository mensajeRepository;
    private final TextoRepository textoRepository;
    private final EquipoUsuariosRepository equipoUsuariosRepository;

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final IUsuarioService usuarioService;
    private final ISalaService salaService;
    private final IMensajeService mensajeService;
    private final IGrupoService grupoService;

    public ChatService(MensajeRepository mensajeRepository,
                       TextoRepository textoRepository,
                       EquipoUsuariosRepository equipoUsuariosRepository,
                       SimpMessagingTemplate simpMessagingTemplate,
                       IUsuarioService usuarioService, ISalaService salaService,
                       IMensajeService mensajeService, IGrupoService grupoService) {
        this.mensajeRepository = mensajeRepository;
        this.textoRepository = textoRepository;
        this.equipoUsuariosRepository = equipoUsuariosRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.usuarioService = usuarioService;
        this.salaService = salaService;
        this.mensajeService = mensajeService;
        this.grupoService = grupoService;
    }

    public List<ContactoResponse> obtenerContactosSinChatPrevio(String filtro) {
        Usuario usuarioActual = usuarioService.obtenerUsuarioActual();
        List<ContactoResponse> contactosDeUsuario = new ArrayList<>();

        Optional<EquipoUsuarios> equipoDeUsuarioActual = equipoUsuariosRepository
                .findByUsuario(usuarioActual)
                .stream()
                .filter(EquipoUsuarios::getEstado)
                .findFirst();

        if (equipoDeUsuarioActual.isEmpty())
            return contactosDeUsuario;

        Campania campania = equipoDeUsuarioActual.get().getEquipo()
                .getOperacion().getCampania();

        List<Usuario> contactos = usuarioService
                .buscarContactosPorCampania(filtro, campania.getId(), usuarioActual.getId());

        // Transformación de usuario a ContactoResponse
        for (Usuario usuario : contactos) {
            contactosDeUsuario.add(
                    generarContactoResponseChatPrivado(usuario, usuarioActual)
            );
        }

        return contactosDeUsuario;
    }

    public List<ContactoResponse> obtenerContactosConChatPrevio() {
        Usuario usuarioLogeado = usuarioService.obtenerUsuarioActual();
        List<Sala> salasDeUsuarioActual = salaService.obtenerSalasPorUsuario(usuarioLogeado.getId());
        List<ContactoResponse> contactosPreviosDeUsuario = new ArrayList<>();

        for (Sala sala : salasDeUsuarioActual) {
            TipoSala tipoSala = sala.getTipoSala();

            // Chat entre 2 personas
            if (tipoSala == TipoSala.PRIVADO) {
                Optional<Usuario> contacto = salaService.obtenerIntegrantesDeSala(sala.getId())
                        .stream()
                        .map(Integrante::getUsuario)
                        .filter(usuario ->
                                !usuario.getId().equals(usuarioLogeado.getId())
                        )
                        .findFirst();

                contacto.ifPresent(usuario -> contactosPreviosDeUsuario.add(
                        generarContactoResponseChatPrivado(usuario, usuarioLogeado)
                ));
            }
            // Chats grupales
            else {
                contactosPreviosDeUsuario.add(
                        generarContactoResponseChatGrupal(sala, usuarioLogeado)
                );
            }
        }

        return contactosPreviosDeUsuario;
    }

    private ContactoResponse generarContactoResponseChatPrivado(Usuario contacto, Usuario usuarioLogeado) {
        // Obtener sala en caso haya habido contacto previo, sino asigna null
        Sala salaExistente = salaService.obtenerSalaPrivadaPorIntegrantes(
                contacto.getId(), usuarioLogeado.getId());

        // Verificación de nulos
        Boolean existeContactoPrevio = salaExistente != null;
        Long idSalaExistente = existeContactoPrevio ? salaExistente.getId() : null;

        // Asignación nombre corto del contacto
        String nombreCortoDeContacto = UsuarioHelper.obtenerNombreCorto(
                contacto.getPersona().getNombres(), contacto.getPersona().getApellidos()
        );

        // Inicialmente es nulo, si hay contacto previo se llena de información
        DatosMensajeResponse datosMensaje = null;

        // Si sala es diferente de null entonces hay un chat previo
        if (idSalaExistente != null) {
            Mensaje ultimoMensajeDeSala = mensajeService.obtenerUltimoMensajeDeSala(idSalaExistente);

            if (ultimoMensajeDeSala != null) {
                Boolean ultimoMensajeEsDeUsuarioActual = ultimoMensajeDeSala
                        .getUsuario().getId().equals(usuarioLogeado.getId());

                String usernameDeUsuarioQueEnvioMensaje = ultimoMensajeEsDeUsuarioActual
                        ? "Tú" : nombreCortoDeContacto;

                String nuevoTexto = usernameDeUsuarioQueEnvioMensaje + ": ";

                switch (ultimoMensajeDeSala.getTipo()) {
                    case MSG_TEXTO -> nuevoTexto += mensajeService
                            .obtenerTextoDeMensaje(ultimoMensajeDeSala.getId()).getContenido();
                    case MSG_IMAGEN -> nuevoTexto += ultimoMensajeEsDeUsuarioActual
                            ? "Enviaste una imagen." : "Envió una imagen.";
                    case MSG_ARCHIVO -> nuevoTexto += ultimoMensajeEsDeUsuarioActual
                            ? "Enviaste un archivo." : "Envió un archivo.";
                }

                datosMensaje = new DatosMensajeResponse(
                        ultimoMensajeEsDeUsuarioActual, nuevoTexto,
                        ultimoMensajeDeSala.getFechaCreacion()
                );
            }
        }

        DatosUsuarioResponse datosUsuario = new DatosUsuarioResponse(
                contacto.getId(), contacto.getImagenPerfil().getUrl(),
                nombreCortoDeContacto, contacto.getPersona().getInformacion()
        );

        return new ContactoResponse(
                idSalaExistente, TipoSala.PRIVADO,
                datosMensaje, null, datosUsuario, existeContactoPrevio
        );
    }

    private ContactoResponse generarContactoResponseChatGrupal(Sala salaGrupal, Usuario usuarioLogeado) {

        DatosMensajeResponse datosMensaje = null;
        DatosGrupoResponse datosGrupo = null;
        boolean existeContactoPrevio = false;

        if (salaGrupal.getId() != null) {
            Mensaje ultimoMensajeSala = mensajeService.obtenerUltimoMensajeDeSala(salaGrupal.getId());
            Grupo grupo = grupoService.obtenerGrupoPorSala(salaGrupal.getId());

            if (ultimoMensajeSala != null) {
                existeContactoPrevio = true;
                boolean mensajeEsDeUsuarioLogeado = ultimoMensajeSala.getUsuario().getId()
                        .equals(usuarioLogeado.getId());

                String nombreCortoUsuarioDeMensaje = mensajeEsDeUsuarioLogeado ?
                        "Tú" :
                        UsuarioHelper.obtenerNombreCorto(
                                ultimoMensajeSala.getUsuario().getPersona().getNombres(),
                                ultimoMensajeSala.getUsuario().getPersona().getApellidos()
                        );

                String nuevoTexto = nombreCortoUsuarioDeMensaje + ": ";

                switch (ultimoMensajeSala.getTipo()) {
                    case MSG_TEXTO -> nuevoTexto += mensajeService
                            .obtenerTextoDeMensaje(ultimoMensajeSala.getId()).getContenido();
                    case MSG_IMAGEN -> nuevoTexto += mensajeEsDeUsuarioLogeado
                            ? "Enviaste una imagen." : "Envió una imagen.";
                    case MSG_ARCHIVO -> nuevoTexto += mensajeEsDeUsuarioLogeado
                            ? "Enviaste un archivo." : "Envió un archivo.";
                }

                datosMensaje = new DatosMensajeResponse(mensajeEsDeUsuarioLogeado, nuevoTexto,
                        ultimoMensajeSala.getFechaCreacion());
            }

            if (grupo != null) {
                datosGrupo = new DatosGrupoResponse(
                        grupo.getId(), grupo.getImagenGrupo().getUrl(),
                        grupo.getNombre(), grupo.getDescripcion()
                );
            }
        }

        return new ContactoResponse(
                salaGrupal.getId(), salaGrupal.getTipoSala(),
                datosMensaje, datosGrupo, null,
                existeContactoPrevio
        );
    }

    public void enviarMensaje(ChatRequest chatRequest, Principal principal) {
        Usuario usuarioActual = usuarioService.obtenerUsuarioPorID(
                Long.parseLong(principal.getName())
        );

        if (chatRequest.getTipoSala() == TipoSala.PRIVADO)
            enviarMensajePrivado(chatRequest, usuarioActual);
        else
            enviarMensajeGrupal(chatRequest, usuarioActual);
    }

    @Transactional
    public void enviarMensajePrivado(ChatRequest chatRequest, Usuario usuarioActual) {
        Sala salaPrivada = salaService.obtenerSalaPrivadaPorIntegrantes(
                chatRequest.getIdUsuarioDestino(), usuarioActual.getId()
        );
        Usuario usuarioDestino = usuarioService.obtenerUsuarioPorID(chatRequest.getIdUsuarioDestino());

        // Si no hay sala, creamos la sala
        if (salaPrivada == null) {
            salaPrivada = salaService.crearSala(
                    new SalaRequest(chatRequest.getTipoSala(), List.of(
                            new IntegranteRequest(Permiso.NO_APLICA, chatRequest.getIdUsuarioDestino()),
                            new IntegranteRequest(Permiso.NO_APLICA, usuarioActual.getId())
                    ))
            );
        }

        Mensaje nuevoMensaje = new Mensaje(
                null, chatRequest.getTipoMensaje(), LocalDateTime.now(),
                LocalDateTime.now(), salaPrivada, usuarioActual
                );
        nuevoMensaje = mensajeRepository.save(nuevoMensaje);

        // Falta validar que el mensaje sea de texto, archivo o imagen
        Texto nuevoTexto = new Texto(null, chatRequest.getTexto(), nuevoMensaje);
        textoRepository.save(nuevoTexto);

        ChatResponse respuesta = new ChatResponse(
                salaPrivada.getId(),
                nuevoMensaje.getId(),
                UsuarioMapper.usuarioResponse(usuarioActual),
                UsuarioMapper.usuarioResponse(usuarioDestino),
                null,
                chatRequest.getTipoSala(),
                chatRequest.getTipoMensaje(),
                null,
                chatRequest.getTexto(),
                nuevoMensaje.getFechaCreacion()
        );

        simpMessagingTemplate.convertAndSendToUser(
                respuesta.getUsuarioDestino().getId().toString(),
                "/queue/messages",
                respuesta
        );

        simpMessagingTemplate.convertAndSendToUser(
                respuesta.getUsuarioRemitente().getId().toString(),
                "/queue/messages",
                respuesta
        );
    }

    @Transactional
    public void enviarMensajeGrupal(ChatRequest chatRequest, Usuario usuarioActual) {
        // Siempre y cuando el id de sala se envíe, la sala será diferente de null
        Sala salaGrupal = salaService.obtenerSalaPorId(chatRequest.getIdSala());
        // Creacion de mensaje
        Mensaje nuevoMensaje = new Mensaje(
                null,
                chatRequest.getTipoMensaje(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                salaGrupal,
                usuarioActual
        );
        nuevoMensaje = mensajeRepository.save(nuevoMensaje);

        // Creacion de texto (FALTA: No se está validando el tipo de mensaje)
        Texto nuevoTexto = new Texto(
                null,
                chatRequest.getTexto(),
                nuevoMensaje
        );
        textoRepository.save(nuevoTexto);

        Grupo grupoDeSala = grupoService.obtenerGrupoPorSala(salaGrupal.getId());

        GrupoResponse grupoResponse = new GrupoResponse(
                grupoDeSala.getId(),
                grupoDeSala.getNombre(),
                grupoDeSala.getDescripcion(),
                grupoDeSala.getImagenGrupo().getUrl(),
                null,
                grupoDeSala.getUltimaModificacion(),
                grupoDeSala.getEstado()
        );

        ChatResponse respuesta = new ChatResponse(
                salaGrupal.getId(),
                nuevoMensaje.getId(),
                UsuarioMapper.usuarioResponse(usuarioActual),
                null,
                grupoResponse,
                chatRequest.getTipoSala(),
                chatRequest.getTipoMensaje(),
                null,
                chatRequest.getTexto(),
                nuevoMensaje.getFechaCreacion()
        );

        simpMessagingTemplate.convertAndSend(
                "/topic/group." + chatRequest.getIdSala(),
                respuesta
        );
    }

    /*
        dto contiene: content, targetType (GROUP/PRIVATE), targetId
        if(chatRequest.isGroup()) {
            simp.convertAndSend("/topic/group." + dto.getTargetId(), dto);
        } else { // privado: usar destino de usuario
            simp.convertAndSendToUser(dto.getTargetId().toString(), "/queue/messages", dto);
        }
        persistir mensaje en DB (MessageRepository.save(...))
    */

}
