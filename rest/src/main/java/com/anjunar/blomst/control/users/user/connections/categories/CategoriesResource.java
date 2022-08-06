package com.anjunar.blomst.control.users.user.connections.categories;

import com.anjunar.blomst.control.users.user.connections.categories.category.CategoryForm;
import com.anjunar.blomst.control.users.user.connections.categories.category.CategoryResource;
import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.blomst.control.users.Category;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.WebURLBuilderFactory.*;

@Path("control/users/user/connections/connection/categories")
@ApplicationScoped
public class CategoriesResource implements ListResourceTemplate<CategoryForm, CategoriesSearch> {

    private final CategoriesService service;

    @Inject
    public CategoriesResource(CategoriesService service) {
        this.service = service;
    }

    public CategoriesResource() {
        this(null);
    }

    @Override
    @LinkDescription("Table Category")
    @RolesAllowed({"Administrator", "User"})
    @Transactional
    public Table<CategoryForm> list(CategoriesSearch search) {

        final long count = service.count(search);
        final List<Category> categories = service.find(search);
        final List<CategoryForm> resources = new ArrayList<>();

        for (Category category : categories) {
            CategoryForm form = CategoryForm.factory(category);

            linkTo(methodOn(CategoryResource.class).read(form.getId()))
                    .build(form::addLink);

            resources.add(form);
        }

        Table<CategoryForm> table = new Table<>(resources, count) {};

        linkTo(methodOn(CategoryResource.class).create())
                .build(table::addLink);

        return table;
    }
}
