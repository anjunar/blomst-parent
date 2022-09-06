package com.anjunar.blomst.social.timeline.post.comments;

import com.anjunar.blomst.social.timeline.post.comments.comment.CommentForm;
import com.anjunar.blomst.social.timeline.post.comments.comment.CommentResource;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.blomst.social.timeline.Comment;

import com.anjunar.common.rest.mapper.ResourceEntityMapper;
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
    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Table Comment")
    public Table<CommentForm> list(CommentsSearch search) {

        List<Comment> comments = service.find(search);
        long count = service.count(search);

        List<CommentForm> resources = new ArrayList<>();

        for (Comment comment : comments) {
            CommentForm resource = mapper.map(comment, CommentForm.class);

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
