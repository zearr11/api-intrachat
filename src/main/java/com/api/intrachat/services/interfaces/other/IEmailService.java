package com.api.intrachat.services.interfaces.other;

import jakarta.mail.MessagingException;

public interface IEmailService {


    void enviarCredencialesACorreo(String correoDestino, String asunto,
                                   String nombres, String apellidos, String password);

    void enviarEmail(String correoDestino, String asunto,
                     String nombres, String apellidos, String password) throws MessagingException;

    String processTemplate(String nombreTemplateHTML, String nombres,
                           String apellidos, String correoDestino,
                           String password);
}
