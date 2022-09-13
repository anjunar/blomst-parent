package com.anjunar.blomst;

import com.anjunar.common.filedisk.Image;
import com.anjunar.common.i18n.Language;
import com.anjunar.common.security.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.apache.commons.io.IOUtils;

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
import java.util.List;
import java.util.Locale;

@ApplicationScoped
public class StartUp {

    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) ServletContext init, IdentityService service, EntityManager entityManager) throws SQLException {

        Language german = null;
        try {
            german = entityManager.createQuery("select l from Language l where l.locale = :locale", Language.class)
                    .setParameter("locale", Locale.forLanguageTag("de-DE"))
                    .getSingleResult();
        } catch (NoResultException e) {
            german = null;
        }

        if (german == null) {
            german = new Language();
            german.setLocale(Locale.forLanguageTag("de-DE"));
            german.getI18nName().put(Locale.forLanguageTag("de-DE"), "Deutsch");
            german.getI18nName().put(Locale.forLanguageTag("en-DE"), "German");
            entityManager.persist(german);
        }

        Language english = null;
        try {
            english = entityManager.createQuery("select l from Language l where l.locale = :locale", Language.class)
                    .setParameter("locale", Locale.forLanguageTag("en-DE"))
                    .getSingleResult();
        } catch (NoResultException e) {
            english = null;
        }

        if (english == null) {
            english = new Language();
            english.setLocale(Locale.forLanguageTag("en-DE"));
            english.getI18nName().put(Locale.forLanguageTag("de-DE"), "Englisch");
            english.getI18nName().put(Locale.forLanguageTag("en-DE"), "English");
            entityManager.persist(english);
        }

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
            patrick.setLanguage(english);
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

        }

        Category category;
        try {
            category = entityManager.createQuery("select c from Category c where function('jsonPathAsText', c.i18nName, :locale) = :name", Category.class)
                    .setParameter("locale", Locale.forLanguageTag("en-DE"))
                    .setParameter("name", "Everybody")
                    .getSingleResult();
        } catch (NoResultException e) {
            category = null;
        }

        if (category == null) {
            category = new Category();
            category.getI18nName().put(Locale.forLanguageTag("de-DE"), "Jeder");
            category.getI18nName().put(Locale.forLanguageTag("en-DE"), "Everybody");
            entityManager.persist(category);
        }

    }

}
