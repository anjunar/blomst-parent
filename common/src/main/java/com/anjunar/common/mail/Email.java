package com.anjunar.common.mail;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Stateless
public class Email {

    private static final Logger log = LoggerFactory.getLogger(Email.class);

    @Resource(name = "java:jboss/mail/Default")
    Session session;

    public void send(String to, String subject, String body) {

        try {
            InternetAddress toAddress = InternetAddress.parse(to)[0];
            InternetAddress fromAddress = InternetAddress.parse("anjunar@gmx.de")[0];

            Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, toAddress);
            message.setFrom(fromAddress);
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");

            Transport.send(message);
        } catch (MessagingException e) {
            log.error(e.getLocalizedMessage());
        }

    }

}
