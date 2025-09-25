package com.api.intrachat.services.impl.communication;

import com.api.intrachat.models.communication.Message;
import com.api.intrachat.models.entity.User;
import com.api.intrachat.models.mode.Individual;
import com.api.intrachat.models.mode.IndividualUser;
import com.api.intrachat.repositories.communication.MessageRepository;
import com.api.intrachat.repositories.mode.IndividualRepository;
import com.api.intrachat.repositories.mode.IndividualUserRepository;
import com.api.intrachat.repositories.mode.projections.IndividualUserProjection;
import com.api.intrachat.services.interfaces.communication.IMessageService;
import com.api.intrachat.services.interfaces.entity.IUserService;
import com.api.intrachat.utils.dto.request.MessageGroupRequest;
import com.api.intrachat.utils.dto.request.MessageIndividualRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService implements IMessageService {

    private final MessageRepository messageRepository;
    private final IUserService userService;
    private final IndividualUserRepository individualUserRepository;
    private final IndividualRepository individualRepository;

    public MessageService(MessageRepository messageRepository,
                          IUserService userService,
                          IndividualUserRepository individualUserRepository,
                          IndividualRepository individualRepository) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.individualUserRepository = individualUserRepository;
        this.individualRepository = individualRepository;
    }

    @Transactional
    @Override
    public Message saveMessageIndividual(MessageIndividualRequest request) {

        Message newMessage;

        // Definir usuario sender y receiver
        User userSender = userService.findUserByEmail(request.getSender());
        User userReceiver = userService.findUserByEmail(request.getReceiver());

        // Declaración de chatIndividual para reutilizar
        Individual chatIndividual;

        // PreviousChat -> Si existe ya hay un chat previo
        Optional<IndividualUserProjection> previousChat = individualUserRepository
                .validateIndividualChatExisting(
                        userSender.getIdUser(), userReceiver.getIdUser()
                );

        // Crear cuerpo de mensaje
        newMessage = Message.builder()
                .user(userSender)
                .messageType(request.getType())
                .content(request.getContent())
                .dateSend(LocalDateTime.now())
                .build();

        // Guardamos mensaje
        newMessage = messageRepository.save(newMessage);

        /*
            if (request.getType().equals(MessageType.FILE)) {
                    MessageFile newMessageFile = MessageFile.builder().build();
                    // Falta implementar en caso de archivos en lugar de texto
                }
        */

        // Asociamos mensaje al chat previo si existe
        if (previousChat.isPresent()) {

            // Buscamos el obj
            chatIndividual = individualRepository.findById(
                    previousChat.get().getIdIndividual()
            ).orElse(null);

            if (chatIndividual != null) {
                // Agregamos el mensaje y guardamos
                chatIndividual.addMessage(newMessage);
                individualRepository.save(chatIndividual);
            }

        } else {

            // Si no hay chat previo, lo creamos
            chatIndividual = Individual.builder()
                    .creationDate(LocalDateTime.now())
                    .messages(new HashSet<>())
                    .build();

            // Agregamos el mensaje y guardamos
            chatIndividual.addMessage(newMessage);
            individualRepository.save(chatIndividual);

            // Creamos la relación de ambos usuarios
            IndividualUser individualUserOne = IndividualUser
                    .builder()
                    .user(userSender)
                    .individual(chatIndividual)
                    .build();
            IndividualUser individualUserTwo = IndividualUser
                    .builder()
                    .user(userReceiver)
                    .individual(chatIndividual)
                    .build();

            // Guardamos la relación
            individualUserRepository.save(individualUserOne);
            individualUserRepository.save(individualUserTwo);

        }

        return newMessage;
    }

    @Override
    public Message saveMessageGroup(MessageGroupRequest request) {
        return null;
    }

    @Override
    public List<Message> getPreviousMessagesChatIndividual() {
        return List.of();
    }

    @Override
    public List<Message> getPreviousMessagesChatGroup() {
        return List.of();
    }

}
