package com.sunrisedental.service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

public class SmtpEmailService
        implements EmailService {

    private final String smtpUsername;
    private final String smtpPassword;

    public SmtpEmailService() {

        this(
                System.getenv("SUNRISE_EMAIL_USERNAME"),
                System.getenv("SUNRISE_EMAIL_PASSWORD")
        );
    }

    public SmtpEmailService(
            final String smtpUsername,
            final String smtpPassword) {

        this.smtpUsername =
                smtpUsername;

        this.smtpPassword =
                smtpPassword;
    }

    @Override
    public void sendEmail(
            final String recipient,
            final String subject,
            final String body) {

        System.out.println("EMAIL DEBUG: trying to send to " + recipient);
        System.out.println(
                "EMAIL USERNAME SET: "
                        + (smtpUsername != null && !smtpUsername.isBlank())
        );
        System.out.println(
                "EMAIL PASSWORD SET: "
                        + (smtpPassword != null && !smtpPassword.isBlank())
        );

        if (recipient == null
                || recipient.isBlank()) {

            throw new IllegalArgumentException(
                    "Recipient email is required");
        }

        if (smtpUsername == null
                || smtpUsername.isBlank()
                || smtpPassword == null
                || smtpPassword.isBlank()) {

            throw new IllegalStateException(
                    "Email credentials are not configured");
        }

        final Properties properties =
                new Properties();

        properties.put(
                "mail.smtp.auth",
                "true");

        properties.put(
                "mail.smtp.starttls.enable",
                "true");

        properties.put(
                "mail.smtp.host",
                "smtp.gmail.com");

        properties.put(
                "mail.smtp.port",
                "587");

        final Session session =
                Session.getInstance(
                        properties,
                        new Authenticator() {

                            @Override
                            protected PasswordAuthentication
                            getPasswordAuthentication() {

                                return new PasswordAuthentication(
                                        smtpUsername,
                                        smtpPassword);
                            }
                        }
                );

        try {

            final Message message =
                    new MimeMessage(
                            session);

            message.setFrom(
                    new InternetAddress(
                            smtpUsername));

            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(
                            recipient));

            message.setSubject(
                    subject);

            message.setText(
                    body);

            Transport.send(
                    message);

        } catch (MessagingException exception) {

            exception.printStackTrace();

            throw new IllegalStateException(
                    "Failed to send appointment email",
                    exception);
        }
    }
}
