package com.api.intrachat.services.impl.message;

import org.springframework.stereotype.Service;

@Service
public class MessageService {
    // implements IMessageService
    /*
    private final IUserService userService;
    private final MessageRepository messageRepository;
    private final MessageTextRepository messageTextRepository;
    private final MessageFileRepository messageFileRepository;
    private final IndividualUserRepository individualUserRepository;
    private final IndividualRepository individualRepository;

    public MessageService(IUserService userService,
                          MessageRepository messageRepository,
                          MessageTextRepository messageTextRepository,
                          MessageFileRepository messageFileRepository,
                          IndividualUserRepository individualUserRepository,
                          IndividualRepository individualRepository) {
        this.userService = userService;
        this.messageRepository = messageRepository;
        this.messageTextRepository = messageTextRepository;
        this.messageFileRepository = messageFileRepository;
        this.individualUserRepository = individualUserRepository;
        this.individualRepository = individualRepository;
    }

    @Override
    public Long getIdChatIndividual(Long idReceiver) {
        Long idSender = userService.getUserCurrent().getIdUser();

        if (idSender.equals(idReceiver))
            return 0L;

        Optional<IndividualUserProjection> previousChat = individualUserRepository
                .validateIndividualChatExisting(idSender, idReceiver);

        return previousChat.isPresent() ?
                previousChat.get().getIdIndividual() : 0L;
    }

    @Transactional
    @Override
    public void saveMessageIndividual(MessageIndividualRequest request) {

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
                .dateSend(LocalDateTime.now())
                .build();

        // Guardamos mensaje general
        newMessage = messageRepository.save(newMessage);

        // Validar si es el mensaje es texto plano o archivo
        if (newMessage.getMessageType().equals(MessageType.TEXT)) {
            MessageText newMessageText = MessageText.builder()
                    .content(request.getContent())
                    .message(newMessage)
                    .build();
            messageTextRepository.save(newMessageText);
        } else {
            // Falta implementar en caso de archivos en lugar de texto
            MessageFile newMessageFile = MessageFile.builder()
                    .fileName(null)
                    .fileType(null)
                    .size(null)
                    .urlFile(null)
                    .message(newMessage)
                    .build();
            messageFileRepository.save(newMessageFile);
        }

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

    }

    @Override
    public void saveMessageGroup(MessageGroupRequest request) {
    }

    @Override
    public PaginatedResponse<List<MessageResponse>> getPreviousMessagesChatIndividual(Long idReceiver, int page, int size) {

        if (page < 1 || size < 1) {
            throw new ErrorException400(ValuesPaginated.MESSAGE_ERROR_LENGTH_PAGE_SIZE);
        }

        Long idIndividual = getIdChatIndividual(idReceiver);
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<MessageProjection> messagePage = messageRepository
                .getPreviosChatsByIdIndividual(idIndividual, pageable);

        List<MessageResponse> messages = messagePage.getContent()
                .stream()
                .map(obj -> {
                    Object msgTypeObj;
                    User userSender = userService.findUserById(obj.getIdUserSender());
                    if (MessageType.valueOf(obj.getMessageType()).equals(MessageType.TEXT)) {
                        msgTypeObj = messageTextRepository.findById(obj.getIdTypeMessage())
                                .orElse(null);
                    } else {
                        msgTypeObj = messageFileRepository.findById(obj.getIdTypeMessage())
                                .orElse(null);
                    }
                    return MessageConvert.messageToResponse(obj, msgTypeObj, userSender);
                })
                .toList().reversed();

        return new PaginatedResponse<>(
                page,
                size,
                messages.size(),
                messagePage.getTotalElements(),
                messagePage.getTotalPages(),
                messages
        );
    }

    @Override
    public PaginatedResponse<List<MessageResponse>> getPreviousMessagesChatGroup(int page, int size) {
        return null;
    }
    */
}
