package com.anjunar.blomst.control.users.user.connections.categories.category;

import com.anjunar.blomst.control.users.UsersResource;
import com.anjunar.blomst.control.users.UsersSearch;
import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.control.users.user.connections.categories.CategoriesResource;
import com.anjunar.blomst.control.users.user.connections.categories.CategoriesSearch;
import com.anjunar.blomst.shared.users.UserSelectResource;
import com.anjunar.blomst.shared.users.UserSelectSearch;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.pages.page.questions.question.answers.AnswersResource;
import com.anjunar.blomst.social.pages.page.questions.question.answers.AnswersSearch;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.Category;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
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

    private final IdentityManager identityManager;

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;
    @Inject
    public CategoryResource(EntityManager entityManager, IdentityManager identityManager, ResourceEntityMapper entityMapper, ResourceRestMapper restMapper) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
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

        form.setOwner(entityMapper.map(identityManager.getUser(), UserSelect.class));

        linkTo(methodOn(CategoryResource.class).save(new CategoryForm()))
                .build(form::addLink);

        JsonObject owner = form.find("owner", JsonObject.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
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
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(owner::addLink);

        return form;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Save Category")
    public ResponseOk save(CategoryForm form) {

        Category entity = restMapper.map(form, Category.class);

        entityManager.persist(entity);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(CategoriesResource.class).list(new CategoriesSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Update Category")
    public ResponseOk update(UUID id, CategoryForm form) {

        restMapper.map(form, Category.class);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(CategoriesResource.class).list(new CategoriesSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Delete Category")
    public ResponseOk delete(UUID id) {

        Category entity = entityManager.find(Category.class, id);

        if (entity.getOwner().equals(identityManager.getUser())) {
            entityManager.remove(entity);
        } else {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(CategoriesResource.class).list(new CategoriesSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }
}
