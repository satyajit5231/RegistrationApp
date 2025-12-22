package com.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.InputStream;
import java.util.Properties;

public class EmailSender {

    public static void sendEmail(String to, String subject, String msg) throws Exception {

        Properties props = new Properties();

        try (InputStream is = EmailSender.class
                .getClassLoader()
                .getResourceAsStream("mail.properties")) {

            if (is == null) {
                throw new RuntimeException("mail.properties not found");
            }
            props.load(is);
        }

        final String from = props.getProperty("mail.from");
        final String password = props.getProperty("mail.password");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(to)
        );
        message.setSubject(subject);
        message.setText(msg);

        Transport.send(message);
    }
}
