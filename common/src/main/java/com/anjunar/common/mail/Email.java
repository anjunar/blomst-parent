package com.anjunar.common.mail;

import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class Email {

    private static final Logger log = LoggerFactory.getLogger(Email.class);

    public void send(String to, String subject, String body) {


    }

}
