package com.anjunar.blomst;

import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.api.ValidationResource;
import com.anjunar.blomst.control.mail.TemplatesResource;
import com.anjunar.blomst.control.mail.TemplatesSearch;
import com.anjunar.blomst.control.notifications.NotificationsResource;
import com.anjunar.blomst.control.notifications.NotificationsSearch;
import com.anjunar.blomst.control.roles.RolesResource;
import com.anjunar.blomst.control.roles.RolesSearch;
import com.anjunar.blomst.control.users.UsersResource;
import com.anjunar.blomst.control.users.UsersSearch;
import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.security.login.LoginResource;
import com.anjunar.blomst.security.logout.LogoutResource;
import com.anjunar.blomst.security.register.RegisterResource;
import com.anjunar.blomst.shared.system.Language;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.communities.CommunitiesResource;
import com.anjunar.blomst.social.communities.CommunitiesSearch;
import com.anjunar.blomst.social.pages.PagesResource;
import com.anjunar.blomst.social.pages.PagesSearch;
import com.anjunar.blomst.social.sites.SitesResource;
import com.anjunar.blomst.social.sites.SitesSearch;
import com.anjunar.blomst.social.timeline.TimelineResource;
import com.anjunar.blomst.social.timeline.TimelineSearch;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.anjunar.common.rest.WebURLBuilderFactory.*;

@Path("/")
public class ApplicationResource implements ValidationResource<UserForm> {

    private final IdentityProvider identityProvider;

    private final EntityManager entityManager;

    @Inject
    public ApplicationResource(IdentityProvider identityProvider, EntityManager entityManager) {
        this.identityProvider = identityProvider;
        this.entityManager = entityManager;
    }

    public ApplicationResource() {
        this(null, null);
    }

    @Override
    @Path("validate")
    @POST
    public ResponseOk validate(UserForm resource) {
        User user;
        try {
            user = entityManager.createQuery("select u from User u where u.firstName = :firstName and u.lastName = :lastName and u.birthDate = :birthDate", User.class)
                    .setParameter("firstName", resource.getFirstName())
                    .setParameter("lastName", resource.getLastName())
                    .setParameter("birthDate", resource.getBirthDate())
                    .getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }

        if (resource.getId() == null) {
            if (user == null) {
                return new ResponseOk();
            } else {
                throw new WebApplicationException(jakarta.ws.rs.core.Response.Status.BAD_REQUEST);
            }
        } else {
            if (user == null) {
                return new ResponseOk();
            } else {
                if (user.getId().equals(resource.getId())) {
                    return new ResponseOk();
                } else {
                    throw new WebApplicationException(jakarta.ws.rs.core.Response.Status.BAD_REQUEST);
                }
            }
        }
    }

    @GET
    @Transactional
    @Path("lang")
    public ResponseOk language(@QueryParam("lang") Locale locale) {

        identityProvider.setLanguage(locale);

        return new ResponseOk();
    }

    @GET
    @Path("language")
    @Produces("application/json")
    public Table<Language> language() {
        List<Language> resources = new ArrayList<>();

        Language german = new Language();
        german.setLanguage("Deutsch");
        german.setLocale(Locale.forLanguageTag("de-DE"));
        resources.add(german);

        Language english = new Language();
        english.setLanguage("English");
        english.setLocale(Locale.forLanguageTag("en-DE"));
        resources.add(english);

        return new Table<>(resources, resources.size()) {};
    }

    @GET
    @Produces("application/json")
    @Transactional
    public UserSelect service() {

        if (identityProvider.isLoggedIn()) {

            UserSelect userSelect = UserSelect.factory(identityProvider.getUser());

            PagesSearch search = new PagesSearch();
            Language language = new Language();
            language.setLocale(identityProvider.getLanguage());
            search.setLanguage(language);

            linkTo(methodOn(PagesResource.class).list(search))
                    .withRel("pages")
                    .build(userSelect::addLink);

            linkTo(methodOn(LogoutResource.class).logout1())
                    .build(userSelect::addLink);

            TimelineSearch timelineSearch = new TimelineSearch();
            linkTo(methodOn(TimelineResource.class).list(timelineSearch))
                    .withRel("timeline")
                    .build(userSelect::addLink);

            linkTo(methodOn(UsersResource.class).list(new UsersSearch()))
                    .withRel("users")
                    .build(userSelect::addLink);

            linkTo(methodOn(RolesResource.class).list(new RolesSearch()))
                    .withRel("roles")
                    .build(userSelect::addLink);

            linkTo(methodOn(TemplatesResource.class).list(new TemplatesSearch()))
                    .withRel("templates")
                    .build(userSelect::addLink);

            NotificationsSearch notificationsSearch = new NotificationsSearch();
            notificationsSearch.setOwner(identityProvider.getUser().getId());
            linkTo(methodOn(NotificationsResource.class).list(notificationsSearch))
                    .withRel("notifications")
                    .build(userSelect::addLink);

            linkTo(methodOn(CommunitiesResource.class).list(new CommunitiesSearch()))
                    .withRel("communities")
                    .build(userSelect::addLink);

            linkTo(methodOn(SitesResource.class).list(new SitesSearch()))
                    .withRel("sites")
                    .build(userSelect::addLink);

            return userSelect;
        } else {
            UserSelect userSelect = new UserSelect();
            userSelect.setLanguage(identityProvider.getLanguage());

            linkTo(methodOn(LoginResource.class).login())
                    .build(userSelect::addLink);


            linkTo(methodOn(RegisterResource.class).register())
                    .build(userSelect::addLink);

            return userSelect;
        }

    }

}