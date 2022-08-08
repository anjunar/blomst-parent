package com.anjunar.blomst.social.timeline.post.comments.comment;

import com.anjunar.blomst.control.users.UsersResource;
import com.anjunar.blomst.control.users.UsersSearch;
import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.rest.MethodPredicate;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.api.json.schema.JsonArray;
import com.anjunar.common.rest.api.json.schema.JsonObject;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.User;
import com.anjunar.blomst.social.timeline.post.comments.CommentsResource;
import com.anjunar.blomst.social.timeline.post.comments.CommentsSearch;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.timeline.Comment;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import java.util.UUID;

import static com.anjunar.common.rest.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.WebURLBuilderFactory.methodOn;

@ApplicationScoped
@Path("/home/timeline/post/comments/comment")
public class CommentResource implements FormResourceTemplate<CommentForm> {

    private final EntityManager entityManager;

    private final IdentityProvider identityProvider;

    @Inject
    public CommentResource(EntityManager entityManager, IdentityProvider identityProvider) {
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;
    }

    public CommentResource() {
        this(null, null);
    }

    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Create Comment")
    public CommentForm create(@QueryParam("post") UUID post, @QueryParam("parent") UUID parent) {
        CommentForm resource = new CommentForm();

        User user = identityProvider.getUser();

        resource.setPost(post);
        resource.setParent(parent);

        resource.setOwner(UserSelect.factory(user));

        linkTo(methodOn(CommentResource.class).save(new CommentForm()))
                .build(resource::addLink);

        JsonArray likes = resource.find("likes", JsonArray.class);
        linkTo(methodOn(UsersResource.class).list(new UsersSearch()))
                .build(likes::addLink);

        return resource;
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Read Comment")
    public CommentForm read(UUID id) {

        Comment comment = entityManager.find(Comment.class, id);

        CommentForm resource = CommentForm.factory(comment);

        linkTo(methodOn(CommentResource.class).update(comment.getId(), new CommentForm()))
                .build(resource::addLink);
        linkTo(methodOn(CommentResource.class).delete(comment.getId()))
                .build(resource::addLink);

        CommentsSearch search = new CommentsSearch();
        search.setParent(id);
        linkTo(methodOn(CommentsResource.class).list(search))
                .withRel("comments")
                .build(resource::addLink);


        JsonArray likes = resource.find("likes", JsonArray.class);
        linkTo(methodOn(UsersResource.class).list(new UsersSearch()))
                .build(likes::addLink);
        JsonObject owner = resource.find("owner", JsonObject.class);
        linkTo(methodOn(UsersResource.class).list(new UsersSearch()))
                .build(owner::addLink);

        return resource;
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Save Comment")
    public CommentForm save(CommentForm resource) {

        Comment comment = new Comment();

        CommentForm.updater(resource, comment, identityProvider, entityManager);

        entityManager.persist(comment);

        resource.setId(comment.getId());

        linkTo(methodOn(CommentResource.class).update(comment.getId(), new CommentForm()))
                .build(resource::addLink);
        linkTo(methodOn(CommentResource.class).delete(comment.getId()))
                .build(resource::addLink);

        return resource;
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @MethodPredicate(OwnerCommentIdentity.class)
    @LinkDescription("Update Comment")
    public CommentForm update(UUID id, CommentForm resource) {
        Comment comment = entityManager.find(Comment.class, id);

        CommentForm.updater(resource, comment, identityProvider, entityManager);

        linkTo(methodOn(CommentResource.class).update(comment.getId(), new CommentForm()))
                .build(resource::addLink);
        linkTo(methodOn(CommentResource.class).delete(comment.getId()))
                .build(resource::addLink);

        return resource;
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @MethodPredicate(OwnerCommentIdentity.class)
    @LinkDescription("Delete Comment")
    public ResponseOk delete(UUID id) {
        Comment comment = entityManager.find(Comment.class, id);
        entityManager.remove(comment);
        return new ResponseOk();
    }
}
