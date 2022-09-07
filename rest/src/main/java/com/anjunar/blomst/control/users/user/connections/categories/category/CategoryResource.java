package com.anjunar.blomst.control.users.user.connections.categories.category;

import com.anjunar.blomst.control.users.UsersResource;
import com.anjunar.blomst.control.users.UsersSearch;
import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.control.users.user.UserResource;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.Category;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@Path("control/users/user/connections/connection/categories/category")
@ApplicationScoped
public class CategoryResource implements FormResourceTemplate<CategoryForm> {

    private final EntityManager entityManager;

    private final IdentityProvider identityProvider;

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;
    @Inject
    public CategoryResource(EntityManager entityManager, IdentityProvider identityProvider, ResourceEntityMapper entityMapper, ResourceRestMapper restMapper) {
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;
        this.entityMapper = entityMapper;
        this.restMapper = restMapper;
    }

    public CategoryResource() {
        this(null, null, null, null);
    }

    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Create Category")
    public CategoryForm create() {
        CategoryForm form = new CategoryForm();

        form.setOwner(entityMapper.map(identityProvider.getUser(), UserForm.class));

        linkTo(methodOn(CategoryResource.class).save(new CategoryForm()))
                .build(form::addLink);

        JsonObject owner = form.find("owner", JsonObject.class);
        linkTo(methodOn(UsersResource.class).list(new UsersSearch()))
                .build(owner::addLink);

        return form;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Read Category")
    public CategoryForm read(UUID id) {

        Category entity = entityManager.find(Category.class, id);

        CategoryForm form = entityMapper.map(entity, CategoryForm.class);

        linkTo(methodOn(CategoryResource.class).update(id, new CategoryForm()))
                .build(form::addLink);
        linkTo(methodOn(CategoryResource.class).delete(id))
                .build(form::addLink);

        JsonObject owner = form.find("owner", JsonObject.class);
        linkTo(methodOn(UsersResource.class).list(new UsersSearch()))
                .build(owner::addLink);

        return form;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Save Category")
    public CategoryForm save(CategoryForm form) {

        Category entity = restMapper.map(form, Category.class);

        entityManager.persist(entity);

        linkTo(methodOn(CategoryResource.class).update(entity.getId(), new CategoryForm()))
                .build(form::addLink);
        linkTo(methodOn(CategoryResource.class).delete(entity.getId()))
                .build(form::addLink);

        return form;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Update Category")
    public CategoryForm update(UUID id, CategoryForm form) {

        restMapper.map(form, Category.class);

        linkTo(methodOn(CategoryResource.class).update(id, new CategoryForm()))
                .build(form::addLink);
        linkTo(methodOn(CategoryResource.class).delete(id))
                .build(form::addLink);

        return form;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Delete Category")
    public ResponseOk delete(UUID id) {

        Category entity = entityManager.find(Category.class, id);

        if (entity.getOwner().equals(identityProvider.getUser())) {
            entityManager.remove(entity);
        } else {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        return new ResponseOk();
    }
}
