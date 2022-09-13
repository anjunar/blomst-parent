package com.anjunar.blomst.social.timeline;

import com.anjunar.blomst.control.users.UsersResource;
import com.anjunar.blomst.control.users.UsersSearch;
import com.anjunar.blomst.shared.users.UserSelectResource;
import com.anjunar.blomst.shared.users.UserSelectSearch;
import com.anjunar.blomst.social.timeline.post.*;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.schema.schema.JsonArray;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.api.ListResourceTemplate;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@ApplicationScoped
@Path("home/timeline")
public class TimelineResource implements ListResourceTemplate<AbstractPostForm, TimelineSearch> {

    private final TimelineService service;

    private final ResourceEntityMapper mapper;


    @Inject
    public TimelineResource(TimelineService service, ResourceEntityMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public TimelineResource() {
        this(null, null);
    }

    @Override
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Table Post")
    public Table<AbstractPostForm> list(TimelineSearch search) {

        if (search.getSource().size() == 0) {
            Set<UUID> connections = service.connections();
            search.setSource(connections);
        }

        List<AbstractPost> posts = service.find(search);
        long count = service.count(search);

        List<AbstractPostForm> resources = new ArrayList<>();

        for (AbstractPost post : posts) {

            AbstractPostForm resource = post.accept(new AbstractPostVisitor<>() {
                @Override
                public AbstractPostForm visit(ImagePost post) {
                    return mapper.map(post, ImagePostForm.class);
                }

                @Override
                public AbstractPostForm visit(LinkPost post) {
                    return mapper.map(post, LinkPostForm.class);
                }

                @Override
                public AbstractPostForm visit(TextPost post) {
                    return mapper.map(post, TextPostForm.class);
                }

                @Override
                public AbstractPostForm visit(SystemPost post) {
                    return mapper.map(post, SystemPostForm.class);
                }
            });

            resources.add(resource);

            linkTo(methodOn(PostResource.class).read(post.getId()))
                            .build(resource::addLink);

        }

        Table<AbstractPostForm> table = new Table<>(resources, count) {};

        JsonArray likes = table.find("likes", JsonArray.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(likes::addLink);

        JsonObject owner = table.find("owner", JsonObject.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(owner::addLink);

        if (search.getSource().size() == 1) {
            linkTo(methodOn(PostResource.class).create("text", search.getSource().iterator().next()))
                    .withRel("create-text")
                    .build(table::addLink);

            linkTo(methodOn(PostResource.class).create("image", search.getSource().iterator().next()))
                    .withRel("create-image")
                    .build(table::addLink);

            linkTo(methodOn(PostResource.class).create("link", search.getSource().iterator().next()))
                    .withRel("create-link")
                    .build(table::addLink);
        }

        return table;
    }

}
