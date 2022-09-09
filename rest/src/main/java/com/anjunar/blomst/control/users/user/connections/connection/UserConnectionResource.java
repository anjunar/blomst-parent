package com.anjunar.blomst.control.users.user.connections.connection;

import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User;
import com.anjunar.common.security.UserConnection;
import com.anjunar.blomst.control.users.user.connections.categories.CategoriesResource;
import com.anjunar.blomst.control.users.user.connections.categories.CategoriesSearch;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;
import static jakarta.ws.rs.core.Response.*;

@Path("control/users/user/connections/connection")
@ApplicationScoped
public class UserConnectionResource implements FormResourceTemplate<UserConnectionForm> {

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    private final UserConnectionService service;

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;


    @Inject
    public UserConnectionResource(EntityManager entityManager, IdentityManager identityManager, UserConnectionService service, ResourceEntityMapper entityMapper, ResourceRestMapper restMapper) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
        this.service = service;
        this.entityMapper = entityMapper;
        this.restMapper = restMapper;
    }

    public UserConnectionResource() {
        this(null, null, null, null, null);
    }

    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Create Connection")
    public UserConnectionForm create(@QueryParam("to") UUID to) {

        UserConnectionForm form = new UserConnectionForm();


        form.setFrom(entityMapper.map(identityManager.getUser(), UserForm.class));
        form.setTo(entityMapper.map(entityManager.find(User.class, to), UserForm.class));

        linkTo(methodOn(UserConnectionResource.class).save(new UserConnectionForm()))
                .build(form::addLink);

        JsonObject category = form.find("category", JsonObject.class);
        linkTo(methodOn(CategoriesResource.class).list(new CategoriesSearch()))
                .build(category::addLink);

        return form;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Read Connection")
    public UserConnectionForm read(UUID id) {
        UserConnection entity = entityManager.find(UserConnection.class, id);

        UserConnectionForm form = entityMapper.map(entity, UserConnectionForm.class);

        form.setFrom(entityMapper.map(entity.getFrom(), UserForm.class));
        form.setTo(entityMapper.map(entity.getTo(), UserForm.class));

        UserConnection acceptedConnection = service.accepted(entity.getFrom().getId(), entity.getTo().getId());
        if (acceptedConnection != null) {
            form.setAccepted(true);
        }

        linkTo(methodOn(UserConnectionResource.class).update(id, new UserConnectionForm()))
                .build(form::addLink);

        linkTo(methodOn(UserConnectionResource.class).delete(id))
                .build(form::addLink);

        JsonObject category = form.find("category", JsonObject.class);
        CategoriesSearch categoriesSearch = new CategoriesSearch();
        categoriesSearch.setOwner(entity.getFrom().getId());
        linkTo(methodOn(CategoriesResource.class).list(categoriesSearch))
                .build(category::addLink);

        return form;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Save Connection")
    public UserConnectionForm save(UserConnectionForm form) {

        UserConnection from = restMapper.map(form, UserConnection.class);
        from.setFrom(identityManager.getUser());
        entityManager.persist(from);

        UserConnection to = new UserConnection();
        to.setFrom(from.getTo());
        to.setTo(from.getFrom());
        entityManager.persist(to);

        service.createNotification(form.getTo().getId(), form.getFrom().getId());

        linkTo(methodOn(UserConnectionResource.class).update(from.getId(), new UserConnectionForm()))
                .build(form::addLink);

        linkTo(methodOn(UserConnectionResource.class).delete(from.getId()))
                .build(form::addLink);

        return form;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Update Connection")
    public UserConnectionForm update(UUID id, UserConnectionForm form) {

        restMapper.map(form, UserConnection.class);

        linkTo(methodOn(UserConnectionResource.class).update(id, new UserConnectionForm()))
                .build(form::addLink);

        linkTo(methodOn(UserConnectionResource.class).delete(id))
                .build(form::addLink);

        return form;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Delete Connection")
    public ResponseOk delete(UUID id) {

        UserConnection entity = entityManager.find(UserConnection.class, id);

        if (entity.getFrom().equals(identityManager.getUser())) {
            entityManager.remove(entity);
        } else {
            throw new WebApplicationException(Status.FORBIDDEN);
        }

        return new ResponseOk();
    }

}
