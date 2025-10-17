package com.api.intrachat.utils.mappers;

public class MessageMapper {
    /*
    public static MessageResponse messageToResponse(MessageProjection messageProjection,
                                                    Object msgTypeObj, User userSender) {

        MessageResponse messageResponse = new MessageResponse(messageProjection.getIdMessage(),
                userSender.getEmail(), messageProjection.getDateSend(),
                MessageType.valueOf(messageProjection.getMessageType()), null, null);

        if (msgTypeObj instanceof MessageText messageText) {
            messageResponse.setMessageText(new MessageTextResponse(messageText.getContent()));
        } else if (msgTypeObj instanceof MessageFile messageFile) {
            messageResponse.setMessageFile(
                    MessageFileResponse.builder()
                            .fileName(messageFile.getFileName())
                            .fileType(messageFile.getFileType())
                            .size(messageFile.getSize())
                            .urlFile(messageFile.getUrlFile())
                            .build()
            );
        }

        return messageResponse;
    }
    */
}
