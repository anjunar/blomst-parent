package com.anjunar.blomst;

import com.anjunar.common.ddd.OnPersist;
import com.anjunar.common.filedisk.Image;
import com.anjunar.common.security.EmailType;
import com.anjunar.common.security.IdentityService;
import com.anjunar.common.security.Role;
import com.anjunar.common.security.User;
import org.apache.commons.io.IOUtils;

import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@ApplicationScoped
public class StartUp {

    @Inject
    @OnPersist
    private Event<User> userEvent;

    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) ServletContext init, IdentityService service) throws SQLException {

        LocalDate birthdate = LocalDate.of(1980, 4, 1);
        User patrick = service.findUser("Patrick", "Bittner", birthdate);

        if (patrick == null) {
            Role administratorRole = new Role();
            administratorRole.setName("Administrator");
            service.saveRole(administratorRole);

            Role userRole = new Role();
            userRole.setName("User");
            service.saveRole(userRole);

            Role guestRole = new Role();
            guestRole.setName("Guest");
            service.saveRole(guestRole);

            patrick = new User();
            patrick.setEnabled(true);
            patrick.setLastName("Bittner");
            patrick.setFirstName("Patrick");
            patrick.setBirthDate(birthdate);
            patrick.setPassword("patrick");
            EmailType emailType = new EmailType();
            emailType.setValue("patrick.bittner@hamburg.de");
            patrick.getEmails().add(emailType);
            patrick.setLanguage(Locale.forLanguageTag("en-DE"));
            patrick.getRoles().add(administratorRole);
            service.saveUser(patrick);

            try {
                URL picture = getClass()
                        .getClassLoader()
                        .getResource("META-INF/resources/user.png");
                InputStream inputStream = picture.openStream();
                byte[] bytes = new byte[inputStream.available()];
                IOUtils.readFully(inputStream, bytes);
                Image image = new Image();
                image.setName("user.png");
                image.setData(bytes);
                image.setLastModified(LocalDateTime.now());
                image.setType("image");
                image.setSubType("png");
                patrick.setPicture(image);
            } catch (IOException e) {
                e.printStackTrace();
            }

            userEvent.fire(patrick);
        }

    }

}
