package com.api.intrachat.controllers.rest;

import com.api.intrachat.dto.generics.GeneralResponse;
import com.api.intrachat.dto.request.customized.ChatRequest;
import com.api.intrachat.services.impl.chat.ChatService;
import com.api.intrachat.services.interfaces.chat.IMensajeService;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.constructs.ResponseConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("${app.prefix}/mensajes")
public class MensajeController {

    private final IMensajeService mensajeService;
    private final ChatService chatService;

    public MensajeController(IMensajeService mensajeService, ChatService chatService) {
        this.mensajeService = mensajeService;
        this.chatService = chatService;
    }

    @GetMapping("/{idSala}")
    public ResponseEntity<GeneralResponse<?>> buscarMensajesPaginado(
            @PathVariable Long idSala,
            @RequestParam(defaultValue = PaginatedConstants.PAGINA_DEFAULT) int page,
            @RequestParam(defaultValue = PaginatedConstants.LONGITUD_DEFAULT) int size,
            @RequestParam(defaultValue = "") String filtro) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(
                        mensajeService.obtenerMensajesPorSala(page, size, filtro, idSala)
                )
        );
    }

    @PostMapping
    public void enviarArchivo(@RequestPart MultipartFile archivo, @RequestPart ChatRequest chatRequest) {
        chatRequest.setArchivo(archivo);
        chatService.enviarMensaje(chatRequest, null);
    }

}
