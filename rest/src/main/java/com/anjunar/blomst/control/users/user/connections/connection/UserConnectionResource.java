package com.anjunar.blomst.control.users.user.connections.connection;

import com.anjunar.blomst.control.users.user.connections.UserConnectionsResource;
import com.anjunar.blomst.control.users.user.connections.UserConnectionsSearch;
import com.anjunar.blomst.shared.users.UserSelectResource;
import com.anjunar.blomst.shared.users.UserSelectSearch;
import com.anjunar.blomst.shared.users.user.UserReference;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.api.Form;
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

import java.util.Objects;
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
    public Form<UserConnectionForm> create(@QueryParam("to") UUID toId) {

        UserConnectionForm resource = new UserConnectionForm();

        Form<UserConnectionForm> form = new Form<>(resource) {};
        form.dirty("from", "to");

        UserReference reference = new UserReference();
        reference.setId(identityManager.getUser().getId());
        resource.setFrom(reference);

        if (Objects.nonNull(toId)) {
            UserReference userReference = new UserReference();
            userReference.setId(toId);
            resource.setTo(userReference);
        }

        linkTo(methodOn(UserConnectionResource.class).save(new UserConnectionForm()))
                .build(form::addLink);

        JsonObject category = form.find("category", JsonObject.class);
        CategoriesSearch search = new CategoriesSearch();
        search.setOwner(identityManager.getUser().getId());
        linkTo(methodOn(CategoriesResource.class).list(search))
                .build(category::addLink);

        JsonObject to = form.find("to", JsonObject.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(to::addLink);


        return form;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Read Connection")
    public Form<UserConnectionForm> read(UUID id) {
        UserConnection entity = entityManager.find(UserConnection.class, id);

        Form<UserConnectionForm> form = entityMapper.map(entity, new Form<>() {});

        UserReference from = new UserReference();
        from.setId(entity.getFrom().getId());
        form.getForm().setFrom(from);
        UserReference to = new UserReference();
        to.setId(entity.getTo().getId());
        form.getForm().setTo(to);

        UserConnection acceptedConnection = service.accepted(entity.getFrom().getId(), entity.getTo().getId());
        if (acceptedConnection != null) {
            form.getForm().setAccepted(true);
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
    public ResponseOk save(UserConnectionForm form) {

        UserConnection from = restMapper.map(form, UserConnection.class);
        from.setFrom(identityManager.getUser());
        entityManager.persist(from);

/*
        UserConnection to = new UserConnection();
        to.setFrom(from.getTo());
        to.setTo(from.getFrom());
        entityManager.persist(to);
*/

        ResponseOk response = new ResponseOk();

        UserConnectionsSearch search = new UserConnectionsSearch();
        search.setFrom(identityManager.getUser().getId());
        linkTo(methodOn(UserConnectionsResource.class).list(search))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Update Connection")
    public ResponseOk update(UUID id, UserConnectionForm form) {

        restMapper.map(form, UserConnection.class);

        ResponseOk response = new ResponseOk();

        UserConnectionsSearch search = new UserConnectionsSearch();
        search.setFrom(identityManager.getUser().getId());
        linkTo(methodOn(UserConnectionsResource.class).list(search))
                .withRel("redirect")
                .build(response::addLink);

        return response;
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

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(UserConnectionsResource.class).list(new UserConnectionsSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

}
