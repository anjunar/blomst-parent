package com.anjunar.blomst.social.timeline.post.comments;

import com.anjunar.blomst.social.timeline.post.comments.comment.CommentForm;
import com.anjunar.blomst.social.timeline.post.comments.comment.CommentResource;
import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.blomst.social.timeline.Comment;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.WebURLBuilderFactory.methodOn;

@ApplicationScoped
@Path("home/timeline/post/comments")
public class CommentsResource implements ListResourceTemplate<CommentForm, CommentsSearch> {

    private final CommentsService service;

    @Inject
    public CommentsResource(CommentsService service) {
        this.service = service;
    }

    public CommentsResource() {
        this(null);
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Table Comment")
    public Table<CommentForm> list(CommentsSearch search) {

        List<Comment> comments = service.find(search);
        long count = service.count(search);

        List<CommentForm> resources = new ArrayList<>();

        for (Comment comment : comments) {
            CommentForm resource = CommentForm.factory(comment);

            resources.add(resource);

            linkTo(methodOn(CommentResource.class).read(comment.getId()))
                    .build(resource::addLink);
        }

        Table<CommentForm> table = new Table<>(resources, count) {};

        linkTo(methodOn(CommentResource.class).create(search.getPost(), search.getParent()))
                .build(table::addLink);

        return table;
    }

}
