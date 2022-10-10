package com.anjunar.blomst.social.timeline.post;

import com.anjunar.blomst.control.users.UsersResource;
import com.anjunar.blomst.control.users.UsersSearch;
import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.shared.users.UserSelectResource;
import com.anjunar.blomst.shared.users.UserSelectSearch;
import com.anjunar.blomst.shared.users.user.IdentitySelect;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.communities.CommunitiesResource;
import com.anjunar.blomst.social.communities.CommunitiesSearch;
import com.anjunar.blomst.social.timeline.*;
import com.anjunar.blomst.social.timeline.post.comments.CommentsResource;
import com.anjunar.blomst.social.timeline.post.comments.CommentsSearch;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.rest.schema.schema.JsonArray;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.rest.MethodPredicate;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.security.Identity;
import com.anjunar.common.security.IdentityManager;

import com.anjunar.common.security.User;
import com.google.common.collect.Sets;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Set;
import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@ApplicationScoped
@Path("home/timeline/post")
public class PostResource implements FormResourceTemplate<AbstractPostForm> {

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;


    @Inject
    public PostResource(EntityManager entityManager, IdentityManager identityManager, ResourceEntityMapper entityMapper, ResourceRestMapper restMapper) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
        this.entityMapper = entityMapper;
        this.restMapper = restMapper;
    }

    public PostResource() {
        this(null, null, null, null);
    }

    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Create Post")
    public AbstractPostForm create(@QueryParam("type") String type, @QueryParam("source") UUID source) {

        AbstractPostForm resource;

        switch (type) {
            case "image" -> {
                resource = new ImagePostForm();
            }
            case "link" -> {
                resource = new LinkPostForm();
            }
            default -> {
                resource = new TextPostForm();
            }
        }

        Identity identity = entityManager.find(Identity.class, source);
        if (identity instanceof User) {
            resource.setSource(entityMapper.map(identity, UserSelect.class));
        } else {
            resource.setSource(entityMapper.map(identity, IdentitySelect.class));
        }
        resource.setOwner(entityMapper.map(identityManager.getUser(), UserSelect.class));

        linkTo(methodOn(PostResource.class).save(new TextPostForm()))
                        .build(resource::addLink);

        return resource;
    }

    @Override
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Read Post")
    public AbstractPostForm read(UUID id) {

        AbstractPost post = entityManager.find(AbstractPost.class, id);

        post.setViews(post.getViews() == null ? 0 : post.getViews() + 1);

        AbstractPostForm resource = post.accept(new AbstractPostVisitor<>() {
            @Override
            public AbstractPostForm visit(ImagePost post) {
                return entityMapper.map(post, ImagePostForm.class);
            }

            @Override
            public AbstractPostForm visit(LinkPost post) {
                return entityMapper.map(post, LinkPostForm.class);
            }

            @Override
            public AbstractPostForm visit(TextPost post) {
                return entityMapper.map(post, TextPostForm.class);
            }

            @Override
            public AbstractPostForm visit(SystemPost post) {
                return entityMapper.map(post, SystemPostForm.class);
            }
        });

        linkTo(methodOn(PostResource.class).update(post.getId(), new TextPostForm()))
                .build(resource::addLink);
        linkTo(methodOn(PostResource.class).delete(post.getId()))
                .build(resource::addLink);

        JsonArray likes = resource.find("likes", JsonArray.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(likes::addLink);

        JsonObject owner = resource.find("owner", JsonObject.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(owner::addLink);

        CommentsSearch search = new CommentsSearch();
        search.setPost(post.getId());
        linkTo(methodOn(CommentsResource.class).list(search))
                .withRel("comments")
                .build(resource::addLink);

        return resource;
    }

    @Override
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Save Post")
    public ResponseOk save(AbstractPostForm resource) {

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


        post = post.accept(new AbstractPostVisitor<>() {
            @Override
            public AbstractPost visit(ImagePost post) {
                return restMapper.map(resource, ImagePost.class);
            }

            @Override
            public AbstractPost visit(LinkPost post) {
                return restMapper.map(resource, LinkPost.class);
            }

            @Override
            public AbstractPost visit(TextPost post) {
                return restMapper.map(resource, TextPost.class);
            }

            @Override
            public AbstractPost visit(SystemPost post) {
                return restMapper.map(resource, SystemPost.class);
            }
        });

        for (User like : post.getLikes()) {
            if (! like.equals(identityManager.getUser())) {
                throw new WebApplicationException(Response.Status.CONFLICT);
            }
        }

        entityManager.persist(post);

        resource.setId(post.getId());

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(TimelineResource.class).list(new TimelineSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @Override
    @RolesAllowed({"Administrator", "User", "Guest"})
    @MethodPredicate(OwnerPostIdentity.class)
    @LinkDescription("Update Post")
    public ResponseOk update(UUID id, AbstractPostForm resource) {
        AbstractPost post = entityManager.find(AbstractPost.class, id);
        Set<User> rawLikes = Sets.newHashSet(post.getLikes());

        post = post.accept(new AbstractPostVisitor<>() {
            @Override
            public AbstractPost visit(ImagePost post) {
                return  restMapper.map(resource, ImagePost.class);
            }

            @Override
            public AbstractPost visit(LinkPost post) {
                return  restMapper.map(resource, LinkPost.class);
            }

            @Override
            public AbstractPost visit(TextPost post) {
                return  restMapper.map(resource, TextPost.class);
            }

            @Override
            public AbstractPost visit(SystemPost post) {
                return  restMapper.map(resource, SystemPost.class);
            }
        });

        linkTo(methodOn(TimelineResource.class).list(new TimelineSearch()))
                .withRel("redirect")
                .build(resource::addLink);
        linkTo(methodOn(PostResource.class).update(post.getId(), new TextPostForm()))
                .build(resource::addLink);
        linkTo(methodOn(PostResource.class).delete(post.getId()))
                .build(resource::addLink);

        Set<User> likes = Sets.newHashSet(post.getLikes());
        likes.removeAll(rawLikes);

        for (User like : likes) {
            if (! like.equals(identityManager.getUser())) {
                throw new WebApplicationException(Response.Status.CONFLICT);
            }
        }

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(TimelineResource.class).list(new TimelineSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @Override
    @RolesAllowed({"Administrator", "User", "Guest"})
    @MethodPredicate(OwnerPostIdentity.class)
    @Produces(MediaType.APPLICATION_JSON)
    @LinkDescription("Delete Post")
    public ResponseOk delete(UUID id) {
        AbstractPost userPost = entityManager.getReference(AbstractPost.class, id);
        entityManager.remove(userPost);
        ResponseOk response = new ResponseOk();

        linkTo(methodOn(TimelineResource.class).list(new TimelineSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }
}
