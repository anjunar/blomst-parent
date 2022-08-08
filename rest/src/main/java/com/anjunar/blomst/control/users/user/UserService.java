package com.anjunar.blomst.control.users.user;

import com.google.common.base.Strings;
import com.anjunar.common.ddd.OnPersist;
import com.anjunar.common.mail.Email;
import com.anjunar.common.mail.Template;
import com.anjunar.common.security.EmailType;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.User;
import com.anjunar.blomst.control.users.Resume;
import com.anjunar.blomst.control.users.UserConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final Email email;

    private final EntityManager entityManager;

    private final IdentityProvider identityProvider;

    @OnPersist
    private final Event<User> userEvent;

    @Inject
    public UserService(Email email, EntityManager entityManager, IdentityProvider identityProvider, Event<User> userEvent) {
        this.email = email;
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;
        this.userEvent = userEvent;
    }

    public UserService() {
        this(null, null, null, null);
    }

    public Resume findResume(User user) {
        return entityManager.createQuery("select r from Resume r where r.owner.id = :user", Resume.class)
                .setParameter("user", user.getId())
                .getSingleResult();
    }

    public UserConnection findConnection(UUID from, UUID to) {
        return entityManager.createQuery("select c from UserConnection c where c.from.id = :from and c.to.id = :to", UserConnection.class)
                .setParameter("from", from)
                .setParameter("to", to)
                .getSingleResult();
    }

    public void confirmationEmail(User user, HttpServletRequest request) {
        for (EmailType email : user.getEmails()) {
            if (!Strings.isNullOrEmpty(email.getValue()) && !email.isConfirmed()) {
                Template template = null;
                try {
                    template = entityManager.createQuery("select t from Template t where t.name = :name and t.language = :language", Template.class)
                            .setParameter("name", "Email Confirmation")
                            .setParameter("language", identityProvider.getLanguage())
                            .getSingleResult();

                    Random rnd = new Random();
                    int number = rnd.nextInt(9999);
                    String hash = String.format("%4d", number).replace(" ", "0");
                    email.setHash(hash);

                    String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

                    String replaced = template.getHtml();
                    replaced = replaced.replace("{{firstName}}", user.getFirstName());
                    replaced = replaced.replace("{{lastName}}", user.getLastName());
                    replaced = replaced.replace("{{link}}", url + "#/anjunar/control/confirm?hash=" + hash + "&id=" + user.getId());

                    this.email.send(email.getValue(), "Email Confirmation", replaced);
                } catch (Exception e) {
                    log.error(e.getLocalizedMessage());
                }
        }
        }
    }

}
