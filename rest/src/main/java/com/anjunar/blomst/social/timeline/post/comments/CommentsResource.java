package com.anjunar.blomst.social.timeline.post.comments;

import com.anjunar.blomst.shared.users.UserSelectResource;
import com.anjunar.blomst.shared.users.UserSelectSearch;
import com.anjunar.blomst.social.timeline.TimelineResource;
import com.anjunar.blomst.social.timeline.TimelineSearch;
import com.anjunar.blomst.social.timeline.post.comments.comment.CommentForm;
import com.anjunar.blomst.social.timeline.post.comments.comment.CommentResource;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.blomst.social.timeline.Comment;

import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.schema.schema.JsonArray;
import com.anjunar.common.rest.schema.schema.JsonObject;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.link.WebURLBuilderFactory.methodOn;

@ApplicationScoped
@Path("home/timeline/post/comments")
public class CommentsResource implements ListResourceTemplate<CommentForm, CommentsSearch> {

    private final CommentsService service;

    private final ResourceEntityMapper mapper;


    @Inject
    public CommentsResource(CommentsService service, ResourceEntityMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public CommentsResource() {
        this(null, null);
    }

    @Override
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Table Comment")
    public Table<CommentForm> list(CommentsSearch search) {

        List<Comment> comments = service.find(search);
        long count = service.count(search);

        List<CommentForm> resources = new ArrayList<>();
        Table<CommentForm> table = new Table<>(resources, count) {};


        for (Comment comment : comments) {
            CommentForm resource = mapper.map(comment, CommentForm.class, table);

            resources.add(resource);

            linkTo(methodOn(CommentResource.class).read(comment.getId()))
                    .build(resource::addLink);
        }

        linkTo(methodOn(CommentResource.class).create(search.getPost(), search.getParent()))
                .build(table::addLink);

        JsonObject post = table.find("post", JsonObject.class);
        linkTo(methodOn(TimelineResource.class).list(new TimelineSearch()))
                .build(post::addLink);

        JsonObject parent = table.find("parent", JsonObject.class);
        linkTo(methodOn(CommentsResource.class).list(new CommentsSearch()))
                .build(parent::addLink);

        JsonObject owner = table.find("owner", JsonObject.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(owner::addLink);

        return table;
    }

}
