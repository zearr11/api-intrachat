package com.api.intrachat.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.prefix}/messages")
public class MessageController {
    /*
    private final IMessageService messageService;

    public MessageController(IMessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<GeneralResponse<?>> getPreviousMessagesChatIndividual(
            @RequestParam Long idReceiver,
            @RequestParam(defaultValue = ValuesPaginated.PAGE_DEFAULT) int page,
            @RequestParam(defaultValue = ValuesPaginated.SIZE_NUMBER_MESSAGES_DEFAULT) int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.success(
                        messageService.getPreviousMessagesChatIndividual(idReceiver, page, size)
                )
        );
    }
    */
}
