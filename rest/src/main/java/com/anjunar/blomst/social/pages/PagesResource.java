package com.anjunar.blomst.social.pages;

import com.anjunar.blomst.social.pages.page.PageResource;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.blomst.ApplicationResource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.link.WebURLBuilderFactory.methodOn;

@ApplicationScoped
@Path("pages")
public class PagesResource implements ListResourceTemplate<PagesForm, PagesSearch> {

    private final PagesService service;

    @Inject
    public PagesResource(PagesService service) {
        this.service = service;
    }

    public PagesResource() {
        this(null);
    }

    @Override
    @LinkDescription("Table Pages")
    @RolesAllowed({"Administrator", "User", "Guest"})
    public Table<PagesForm> list(PagesSearch search) {

        long count = service.count(search);
        List<Page> pages = service.find(search);

        List<PagesForm> resources = new ArrayList<>();
        for (Page page : pages) {
            PagesForm resource = PagesForm.factory(page);

            linkTo(methodOn(PageResource.class).read(page.getId(), null))
                    .build(resource::addLink);

            resources.add(resource);
        }

        Table<PagesForm> table = new Table<>(resources, count) {};

        JsonObject language = table.find("language", JsonObject.class);
        linkTo(methodOn(ApplicationResource.class).language())
                .withRel("list")
                .build(language::addLink);

        linkTo(methodOn(PageResource.class).create())
                .build(table::addLink);

        return table;
    }
}
