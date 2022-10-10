package com.anjunar.blomst.social.timeline.post.comments.comment;

import com.anjunar.blomst.shared.users.UserSelectResource;
import com.anjunar.blomst.shared.users.UserSelectSearch;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.timeline.AbstractPost;
import com.anjunar.blomst.social.timeline.Comment;
import com.anjunar.blomst.social.timeline.post.AbstractPostForm;
import com.anjunar.blomst.social.timeline.post.comments.CommentsResource;
import com.anjunar.blomst.social.timeline.post.comments.CommentsSearch;
import com.anjunar.common.rest.MethodPredicate;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.rest.schema.schema.JsonArray;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User;
import com.google.common.collect.Sets;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.link.WebURLBuilderFactory.methodOn;

@ApplicationScoped
@Path("/home/timeline/post/comments/comment")
public class CommentResource implements FormResourceTemplate<CommentForm> {

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;


    @Inject
    public CommentResource(EntityManager entityManager, IdentityManager identityManager, ResourceEntityMapper mapper, ResourceRestMapper restMapper) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
        this.entityMapper = mapper;
        this.restMapper = restMapper;
    }

    public CommentResource() {
        this(null, null, null, null);
    }

    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Create Comment")
    public CommentForm create(@QueryParam("post") UUID postId, @QueryParam("parent") UUID commentId) {
        CommentForm resource = new CommentForm();

        User user = identityManager.getUser();

        AbstractPost post = entityManager.find(AbstractPost.class, postId);
        resource.setPost(entityMapper.map(post, AbstractPostForm.class));
        resource.setOwner(entityMapper.map(user, UserSelect.class));

        if (Objects.nonNull(commentId)) {
            Comment parent = entityManager.find(Comment.class, commentId);
            resource.setParent(entityMapper.map(parent, CommentForm.class));
        }

        linkTo(methodOn(CommentResource.class).save(new CommentForm()))
                .build(resource::addLink);

        JsonArray likes = resource.find("likes", JsonArray.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(likes::addLink);

        return resource;
    }

    @Override
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Read Comment")
    public CommentForm read(UUID id) {

        Comment comment = entityManager.find(Comment.class, id);

        CommentForm resource = entityMapper.map(comment, CommentForm.class);

        linkTo(methodOn(CommentResource.class).update(comment.getId(), new CommentForm()))
                .build(resource::addLink);
        linkTo(methodOn(CommentResource.class).delete(comment.getId()))
                .build(resource::addLink);

        CommentsSearch search = new CommentsSearch();
        search.setParent(comment.getId());
        search.setPost(comment.getPost().getId());
        linkTo(methodOn(CommentsResource.class).list(search))
                .withRel("comments")
                .build(resource::addLink);


        JsonArray likes = resource.find("likes", JsonArray.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(likes::addLink);
        JsonObject owner = resource.find("owner", JsonObject.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(owner::addLink);

        return resource;
    }

    @Override
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Save Comment")
    public ResponseOk save(CommentForm resource) {

        Comment comment = restMapper.map(resource, Comment.class);

        for (User like : comment.getLikes()) {
            if (!like.equals(identityManager.getUser())) {
                throw new WebApplicationException(Response.Status.CONFLICT);
            }
        }

        entityManager.persist(comment);

        resource.setId(comment.getId());

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(CommentsResource.class).list(new CommentsSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @Override
    @RolesAllowed({"Administrator", "User", "Guest"})
    @MethodPredicate(OwnerCommentIdentity.class)
    @LinkDescription("Update Comment")
    public ResponseOk update(UUID id, CommentForm resource) {
        Comment rawComment = entityManager.find(Comment.class, id);
        Set<User> rawLikes = Sets.newHashSet(rawComment.getLikes());

        Comment comment = restMapper.map(resource, Comment.class);

        Set<User> likes = Sets.newHashSet(comment.getLikes());
        likes.removeAll(rawLikes);

        for (User like : likes) {
            if (!like.equals(identityManager.getUser())) {
                throw new WebApplicationException(Response.Status.CONFLICT);
            }
        }

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(CommentsResource.class).list(new CommentsSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @Override
    @RolesAllowed({"Administrator", "User", "Guest"})
    @MethodPredicate(OwnerCommentIdentity.class)
    @LinkDescription("Delete Comment")
    public ResponseOk delete(UUID id) {
        Comment comment = entityManager.find(Comment.class, id);
        entityManager.remove(comment);
        ResponseOk response = new ResponseOk();

        linkTo(methodOn(CommentsResource.class).list(new CommentsSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }
}
