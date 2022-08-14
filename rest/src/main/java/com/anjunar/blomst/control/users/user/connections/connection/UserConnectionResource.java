package com.anjunar.blomst.control.users.user.connections.connection;

import com.anjunar.blomst.social.sites.SiteConnection;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.objectmapper.NewInstanceProvider;
import com.anjunar.common.rest.objectmapper.ObjectMapper;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.User;
import com.anjunar.blomst.control.users.UserConnection;
import com.anjunar.blomst.control.users.user.connections.categories.CategoriesResource;
import com.anjunar.blomst.control.users.user.connections.categories.CategoriesSearch;
import com.anjunar.blomst.shared.users.user.UserSelect;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;
import static jakarta.ws.rs.core.Response.*;

@Path("control/users/user/connections/connection")
@ApplicationScoped
public class UserConnectionResource implements FormResourceTemplate<UserConnectionForm> {

    private final EntityManager entityManager;

    private final IdentityProvider identityProvider;

    private final UserConnectionService service;

    @Inject
    public UserConnectionResource(EntityManager entityManager, IdentityProvider identityProvider, UserConnectionService service) {
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;
        this.service = service;
    }

    public UserConnectionResource() {
        this(null, null, null);
    }

    @Transactional
    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Create Connection")
    public UserConnectionForm create(@QueryParam("to") UUID to) {

        UserConnectionForm form = new UserConnectionForm();

        ObjectMapper mapper = new ObjectMapper();

        form.setFrom(mapper.map(identityProvider.getUser(), UserSelect.class));
        form.setTo(mapper.map(entityManager.find(User.class, to), UserSelect.class));

        linkTo(methodOn(UserConnectionResource.class).save(new UserConnectionForm()))
                .build(form::addLink);

        JsonObject category = form.find("category", JsonObject.class);
        linkTo(methodOn(CategoriesResource.class).list(new CategoriesSearch()))
                .build(category::addLink);

        return form;
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Read Connection")
    public UserConnectionForm read(UUID id) {
        UserConnection entity = entityManager.find(UserConnection.class, id);

        ObjectMapper mapper = new ObjectMapper();
        UserConnectionForm form = mapper.map(entity, UserConnectionForm.class);

        form.setFrom(mapper.map(entity.getFrom(), UserSelect.class));
        form.setTo(mapper.map(entity.getTo(), UserSelect.class));

        UserConnection acceptedConnection = service.accepted(entity.getFrom().getId(), entity.getTo().getId());
        if (acceptedConnection != null) {
            form.setAccepted(true);
        }

        linkTo(methodOn(UserConnectionResource.class).update(id, new UserConnectionForm()))
                .build(form::addLink);

        linkTo(methodOn(UserConnectionResource.class).delete(id))
                .build(form::addLink);

        JsonObject category = form.find("category", JsonObject.class);
        linkTo(methodOn(CategoriesResource.class).list(new CategoriesSearch()))
                .build(category::addLink);

        return form;
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Save Connection")
    public UserConnectionForm save(UserConnectionForm form) {

        NewInstanceProvider instanceProvider = (uuid, sourceClass) -> entityManager.find(sourceClass, uuid);
        ObjectMapper mapper = new ObjectMapper(instanceProvider);
        UserConnection entity = mapper.map(form, UserConnection.class);
        entity.setFrom(identityProvider.getUser());

        entityManager.persist(entity);

        service.createNotification(form.getTo().getId(), form.getFrom().getId());

        linkTo(methodOn(UserConnectionResource.class).update(entity.getId(), new UserConnectionForm()))
                .build(form::addLink);

        linkTo(methodOn(UserConnectionResource.class).delete(entity.getId()))
                .build(form::addLink);

        return form;
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Update Connection")
    public UserConnectionForm update(UUID id, UserConnectionForm form) {

        NewInstanceProvider instanceProvider = (uuid, sourceClass) -> entityManager.find(sourceClass, uuid);
        ObjectMapper mapper = new ObjectMapper(instanceProvider);
        mapper.map(form, UserConnection.class);

        linkTo(methodOn(UserConnectionResource.class).update(id, new UserConnectionForm()))
                .build(form::addLink);

        linkTo(methodOn(UserConnectionResource.class).delete(id))
                .build(form::addLink);

        return form;
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Delete Connection")
    public ResponseOk delete(UUID id) {

        UserConnection entity = entityManager.find(UserConnection.class, id);

        if (entity.getFrom().equals(identityProvider.getUser())) {
            entityManager.remove(entity);
        } else {
            throw new WebApplicationException(Status.FORBIDDEN);
        }

        return new ResponseOk();
    }

}
