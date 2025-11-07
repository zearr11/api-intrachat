package com.api.intrachat.services.impl.other;

import com.api.intrachat.services.interfaces.other.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailService implements IEmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailService(JavaMailSender javaMailSender,
                        SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void enviarCredencialesACorreo(String correoDestino, String asunto,
                                          String nombres, String apellidos, String password) {
        new Thread(() -> {
            try {
                enviarEmail(correoDestino, asunto, nombres, apellidos, password);
            } catch (Exception e) {
                System.out.println("Error al enviar correo electrónico: " + e.getMessage());
            }
        }).start();
    }

    @Async
    @Override
    public void enviarEmail(String correoDestino, String asunto, String nombres,
                          String apellidos, String password) throws MessagingException {
        // Declaración inicial
        MimeMessage mensaje = javaMailSender.createMimeMessage();
        MimeMessageHelper parametros = new MimeMessageHelper(mensaje, true, "UTF-8");

        // Establecer parametros
        parametros.setTo(correoDestino);
        parametros.setSubject(asunto);

        // Procesar template HTML
        String htmlContent = processTemplate("vista_credenciales",
                nombres, apellidos, correoDestino, password
        );

        parametros.setText(htmlContent, true);

        javaMailSender.send(mensaje);
    }

    @Override
    public String processTemplate(String nombreTemplateHTML, String nombres,
                                  String apellidos, String correoDestino, String password) {
        Context context = new Context();
        context.setVariable("nombres", nombres);
        context.setVariable("apellidos", apellidos);
        context.setVariable("correoDestino", correoDestino);
        context.setVariable("password", password);

        return templateEngine.process(nombreTemplateHTML, context);
    }

}
