package com.anjunar.blomst.control.users.user.connections.categories;

import com.anjunar.blomst.control.users.user.connections.categories.category.CategoryForm;
import com.anjunar.blomst.control.users.user.connections.categories.category.CategoryResource;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.security.Category;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@Path("control/users/user/connections/connection/categories")
@ApplicationScoped
public class CategoriesResource implements ListResourceTemplate<CategoryForm, CategoriesSearch> {

    private final CategoriesService service;

    private final ResourceEntityMapper mapper;


    @Inject
    public CategoriesResource(CategoriesService service, ResourceEntityMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public CategoriesResource() {
        this(null, null);
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
            CategoryForm form = mapper.map(category, CategoryForm.class);

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
