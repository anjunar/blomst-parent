package com.anjunar.blomst.social.timeline;

import com.anjunar.blomst.social.timeline.post.*;
import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.rest.api.json.schema.JsonArray;
import com.anjunar.common.rest.api.json.schema.JsonObject;
import com.anjunar.blomst.shared.users.UserSelectResource;
import com.anjunar.blomst.shared.users.UserSelectSearch;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.api.ListResourceTemplate;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.anjunar.common.rest.WebURLBuilderFactory.*;

@ApplicationScoped
@Path("home/timeline")
public class TimelineResource implements ListResourceTemplate<AbstractPostForm, TimelineSearch> {

    private final TimelineService service;

    @Inject
    public TimelineResource(TimelineService service) {
        this.service = service;
    }

    public TimelineResource() {
        this(null);
    }

    @Override
    @Transactional
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
                    return ImagePostForm.factory(post);
                }

                @Override
                public AbstractPostForm visit(LinkPost post) {
                    return LinkPostForm.factory(post);
                }

                @Override
                public AbstractPostForm visit(TextPost post) {
                    return TextPostForm.factory(post);
                }

                @Override
                public AbstractPostForm visit(SystemPost post) {
                    return SystemPostForm.factory(post);
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
