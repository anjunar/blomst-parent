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
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;

import java.util.Objects;
import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;
import static jakarta.ws.rs.core.Response.*;

@Path("control/users/user/connections/connection")
@ApplicationScoped
public class UserConnectionResource {

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
        UserReference reference = new UserReference();
        reference.setId(identityManager.getUser().getId());
        resource.setFrom(reference);

        if (Objects.nonNull(toId)) {
            resource.setTo(restMapper.map(entityManager.find(User.class, toId), UserSelect.class));
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


    @Produces("application/json")
    @GET
    @Path("resolve")
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Read Connection")
    public Form<UserConnectionForm> read(@QueryParam("from") UUID from, @QueryParam("to") UUID to) {
        try {
            UserConnection result = entityManager.createQuery("select c from UserConnection c where c.from.id = :from and c.to.id = :to", UserConnection.class)
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .getSingleResult();
            return read(result.getId());
        } catch (NoResultException e) {
            UserConnectionForm form = new UserConnectionForm();
            form.setStatus(UserConnection.ConnectionStatus.NONE);
            form.setTo(entityMapper.map(entityManager.find(User.class, to), UserSelect.class));
            form.setFrom(entityMapper.map(entityManager.find(User.class, from), UserReference.class));
            return new Form<>(form) {};
        }
    }

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Read Connection")
    @Produces("application/json")
    @GET
    public Form<UserConnectionForm> read(@QueryParam("id") UUID id) {
        UserConnection entity = entityManager.find(UserConnection.class, id);

        Form<UserConnectionForm> form = entityMapper.map(entity, new Form<>() {});

        UserReference from = new UserReference();
        from.setId(entity.getFrom().getId());
        form.getForm().setFrom(from);

        form.getForm().setTo(restMapper.map(entity.getTo(), UserSelect.class));

/*
        UserConnection acceptedConnection = service.accepted(entity.getFrom().getId(), entity.getTo().getId());
        if (acceptedConnection != null) {
            form.getForm().setAccepted(true);
        }
*/

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

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Save Connection")
    @Consumes("application/json")
    @Produces("application/json")
    @POST
    public UserConnectionForm save(@Valid UserConnectionForm form) {

        UserConnection from = restMapper.map(form, UserConnection.class);
        from.setFrom(identityManager.getUser());
        from.setStatus(UserConnection.ConnectionStatus.INITIATED);
        entityManager.persist(from);

        UserConnection to = new UserConnection();
        to.setFrom(from.getTo());
        to.setTo(from.getFrom());
        to.setStatus(UserConnection.ConnectionStatus.PENDING);
        entityManager.persist(to);

        form.setId(from.getId());
        form.setStatus(UserConnection.ConnectionStatus.INITIATED);

        UserConnectionsSearch search = new UserConnectionsSearch();
        search.setFrom(identityManager.getUser().getId());
        linkTo(methodOn(UserConnectionsResource.class).list(search))
                .withRel("redirect")
                .build(form::addLink);

        return form;
    }

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Update Connection")
    @Consumes("application/json")
    @Produces("application/json")
    @PUT
    public UserConnectionForm update(@QueryParam("id") UUID id, @Valid UserConnectionForm form) {

        UserConnection entity = restMapper.map(form, UserConnection.class);
        UserConnection reverse = service.accepted(entity.getTo().getId(), entity.getFrom().getId());

        entity.setFrom(identityManager.getUser());

        switch (entity.getStatus()) {
            case PENDING -> {
                entity.setStatus(UserConnection.ConnectionStatus.ACCEPTED);
                reverse.setStatus(UserConnection.ConnectionStatus.ACCEPTED);
                form.setStatus(UserConnection.ConnectionStatus.ACCEPTED);
            }
            case CANCELED, DECLINED -> {
                entity.setStatus(UserConnection.ConnectionStatus.INITIATED);
                reverse.setStatus(UserConnection.ConnectionStatus.PENDING);
                form.setStatus(UserConnection.ConnectionStatus.INITIATED);
            }
            case default -> {
                form.setStatus(UserConnection.ConnectionStatus.NONE);
                entity.setStatus(UserConnection.ConnectionStatus.NONE);
                reverse.setStatus(UserConnection.ConnectionStatus.NONE);
            }
        }

        UserConnectionsSearch search = new UserConnectionsSearch();
        search.setFrom(identityManager.getUser().getId());
        linkTo(methodOn(UserConnectionsResource.class).list(search))
                .withRel("redirect")
                .build(form::addLink);

        return form;
    }

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Delete Connection")
    @DELETE
    @Produces("application/json")
    public UserConnectionForm delete(@QueryParam("id") UUID id) {

        UserConnection entity = entityManager.find(UserConnection.class, id);
        UserConnection reverse = service.accepted(entity.getTo().getId(), entity.getFrom().getId());

        if (entity.getFrom().equals(identityManager.getUser())) {
            entity.setStatus(UserConnection.ConnectionStatus.DECLINED);
            reverse.setStatus(UserConnection.ConnectionStatus.CANCELED);
        } else {
            throw new WebApplicationException(Status.FORBIDDEN);
        }

        UserConnectionForm form = entityMapper.map(entity, UserConnectionForm.class);

        linkTo(methodOn(UserConnectionsResource.class).list(new UserConnectionsSearch()))
                .withRel("redirect")
                .build(form::addLink);

        return form;
    }

}
