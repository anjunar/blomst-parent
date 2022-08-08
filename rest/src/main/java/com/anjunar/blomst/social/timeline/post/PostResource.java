package com.anjunar.blomst.social.timeline.post;

import com.anjunar.blomst.control.users.UsersResource;
import com.anjunar.blomst.control.users.UsersSearch;
import com.anjunar.blomst.social.timeline.*;
import com.anjunar.blomst.social.timeline.post.comments.CommentsResource;
import com.anjunar.blomst.social.timeline.post.comments.CommentsSearch;
import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.api.json.schema.JsonArray;
import com.anjunar.common.rest.api.json.schema.JsonObject;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.MethodPredicate;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.security.IdentityProvider;

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

import static com.anjunar.common.rest.WebURLBuilderFactory.*;

@ApplicationScoped
@Path("home/timeline/post")
public class PostResource implements FormResourceTemplate<AbstractPostForm> {

    private final EntityManager entityManager;

    private final IdentityProvider identityProvider;

    @Inject
    public PostResource(EntityManager entityManager, IdentityProvider identityProvider) {
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;
    }

    public PostResource() {
        this(null, null);
    }

    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Create Post")
    public AbstractPostForm create(@QueryParam("type") String type, @QueryParam("source") UUID source) {

        AbstractPostForm resource;

        switch (type) {
            case "image" : {
                resource = new ImagePostForm();
            } break;
            case "link" : {
                resource = new LinkPostForm();
            } break;
            default: {
                resource = new TextPostForm();
            } break;
        }

        resource.setSource(source);
        resource.setOwner(UserSelect.factory(identityProvider.getUser()));

        linkTo(methodOn(PostResource.class).save(new TextPostForm()))
                        .build(resource::addLink);

        return resource;
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Read Post")
    public AbstractPostForm read(UUID id) {

        AbstractPost post = entityManager.find(AbstractPost.class, id);

        post.setViews(post.getViews() == null ? 0 : post.getViews() + 1);

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

        linkTo(methodOn(PostResource.class).update(post.getId(), new TextPostForm()))
                .build(resource::addLink);
        linkTo(methodOn(PostResource.class).delete(post.getId()))
                .build(resource::addLink);

        JsonArray likes = resource.find("likes", JsonArray.class);
        linkTo(methodOn(UsersResource.class).list(new UsersSearch()))
                .build(likes::addLink);

        JsonObject owner = resource.find("owner", JsonObject.class);
        linkTo(methodOn(UsersResource.class).list(new UsersSearch()))
                .build(owner::addLink);

        CommentsSearch search = new CommentsSearch();
        search.setPost(post.getId());
        linkTo(methodOn(CommentsResource.class).list(search))
                .withRel("comments")
                .build(resource::addLink);

        return resource;
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Save Post")
    public AbstractPostForm save(AbstractPostForm resource) {

        AbstractPost post = resource.accept(new AbstractPostFormVisitor<>() {
            @Override
            public AbstractPost visit(LinkPostForm form) {
                return new LinkPost();
            }

            @Override
            public AbstractPost visit(ImagePostForm form) {
                return new ImagePost();
            }

            @Override
            public AbstractPost visit(TextPostForm form) {
                return new TextPost();
            }

            @Override
            public AbstractPost visit(SystemPostForm post) {
                return new SystemPost();
            }
        });

        post.accept(new AbstractPostVisitor<>() {
            @Override
            public AbstractPost visit(ImagePost post) {
                return ImagePostForm.updater((ImagePostForm) resource, post, identityProvider, entityManager);
            }

            @Override
            public AbstractPost visit(LinkPost post) {
                return LinkPostForm.updater((LinkPostForm) resource, post, identityProvider, entityManager);
            }

            @Override
            public AbstractPost visit(TextPost post) {
                return TextPostForm.updater((TextPostForm) resource, post, identityProvider, entityManager);
            }

            @Override
            public AbstractPost visit(SystemPost post) {
                return SystemPostForm.updater((SystemPostForm) resource, post, identityProvider, entityManager);
            }
        });

        entityManager.persist(post);

        resource.setId(post.getId());

        linkTo(methodOn(PostResource.class).update(post.getId(), new TextPostForm()))
                .build(resource::addLink);
        linkTo(methodOn(PostResource.class).delete(post.getId()))
                .build(resource::addLink);

        return resource;
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @MethodPredicate(OwnerPostIdentity.class)
    @LinkDescription("Update Post")
    public AbstractPostForm update(UUID id, AbstractPostForm resource) {

        AbstractPost post = entityManager.find(AbstractPost.class, id);

        post.accept(new AbstractPostVisitor<>() {
            @Override
            public AbstractPost visit(ImagePost post) {
                return ImagePostForm.updater((ImagePostForm) resource, post, identityProvider, entityManager);
            }

            @Override
            public AbstractPost visit(LinkPost post) {
                return LinkPostForm.updater((LinkPostForm) resource, post, identityProvider, entityManager);
            }

            @Override
            public AbstractPost visit(TextPost post) {
                return TextPostForm.updater((TextPostForm) resource, post, identityProvider, entityManager);
            }

            @Override
            public AbstractPost visit(SystemPost post) {
                return SystemPostForm.updater((SystemPostForm) resource, post, identityProvider, entityManager);
            }
        });

        linkTo(methodOn(PostResource.class).update(post.getId(), new TextPostForm()))
                .build(resource::addLink);
        linkTo(methodOn(PostResource.class).delete(post.getId()))
                .build(resource::addLink);

        return resource;
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @MethodPredicate(OwnerPostIdentity.class)
    @LinkDescription("Delete Post")
    public ResponseOk delete(UUID id) {
        AbstractPost userPost = entityManager.getReference(AbstractPost.class, id);
        entityManager.remove(userPost);
        return new ResponseOk();
    }
}
