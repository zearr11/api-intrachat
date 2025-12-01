package com.api.intrachat.controllers.rest;

import com.api.intrachat.dto.generics.GeneralResponse;
import com.api.intrachat.services.impl.chat.ChatService;
import com.api.intrachat.utils.constructs.ResponseConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.prefix}/contactos")
public class ContactoController {

    private final ChatService chatService;

    public ContactoController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/recientes")
    public ResponseEntity<GeneralResponse<?>> obtenerContactosConChatsRecientes(@RequestParam(defaultValue = "")
                                                                                String filtroBusqueda) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(chatService.obtenerContactosConChatsRecientes(filtroBusqueda))
        );
    }

    @GetMapping("/grupos")
    public ResponseEntity<GeneralResponse<?>> obtenerContactosDeGrupos(@RequestParam(defaultValue = "")
                                                                         String filtroBusqueda) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(chatService.obtenerContactosDeGrupos(filtroBusqueda))
        );
    }

    @GetMapping("/campania")
    public ResponseEntity<GeneralResponse<?>> obtenerContactosDeCampania(@RequestParam(defaultValue = "")
                                                                             String filtroBusqueda) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.generarRespuestaExitosa(chatService.obtenerContactosDeCampania(filtroBusqueda))
        );
    }

}
