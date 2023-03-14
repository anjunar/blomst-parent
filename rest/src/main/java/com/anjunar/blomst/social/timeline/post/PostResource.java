package com.anjunar.blomst.social.timeline.post;

import com.anjunar.blomst.shared.users.UserSelectResource;
import com.anjunar.blomst.shared.users.UserSelectSearch;
import com.anjunar.blomst.shared.users.user.UserReference;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.timeline.*;
import com.anjunar.blomst.social.timeline.post.comments.CommentsResource;
import com.anjunar.blomst.social.timeline.post.comments.CommentsSearch;
import com.anjunar.blomst.shared.likes.LikesResource;
import com.anjunar.blomst.shared.likes.LikesSearch;
import com.anjunar.common.rest.api.Form;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.rest.schema.schema.JsonBoolean;
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

import java.io.InputStream;
import java.util.Map;
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

    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response upload(Map<String, InputStream> files) {
        return Response.ok().build();
    }

    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Create Post")
    public Form<? extends AbstractPostForm> create(@QueryParam("type") String type, @QueryParam("source") UUID source) {

        AbstractPostForm resource;
        Form<? extends AbstractPostForm> form;

        switch (type) {
            case "image" -> {
                resource = new ImagePostForm();
                form = new Form<ImagePostForm>((ImagePostForm) resource) {};
            }
            case "video" -> {
                resource = new VideoPostForm();
                form = new Form<VideoPostForm>((VideoPostForm) resource) {};
            }
            default -> {
                resource = new TextPostForm();
                form = new Form<TextPostForm>((TextPostForm) resource) {};
            }
        }

        form.dirty("source");

        Identity identity = entityManager.find(Identity.class, source);
        if (identity instanceof User) {
            resource.setSource(entityMapper.map(identity, UserReference.class));
        } else {
            resource.setSource(entityMapper.map(identity, UserReference.class));
        }
        if (identityManager.isLoggedIn()) {
            resource.setOwner(entityMapper.map(identityManager.getUser(), UserSelect.class));
        }

        linkTo(methodOn(PostResource.class).save(new TextPostForm()))
                        .build(form::addLink);

        return form;
    }

    @Override
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Read Post")
    public Form<? extends AbstractPostForm> read(UUID id) {

        AbstractPost post = entityManager.find(AbstractPost.class, id);

        post.setViews(post.getViews() == null ? 0 : post.getViews() + 1);

        Form<? extends AbstractPostForm> resource = post.accept(new AbstractPostVisitor<>() {
            @Override
            public Form<? extends AbstractPostForm> visit(ImagePost post) {
                return entityMapper.map(post, new Form<ImagePostForm>() {});
            }

            @Override
            public Form<? extends AbstractPostForm> visit(VideoPost post) {
                return entityMapper.map(post, new Form<VideoPostForm>() {});
            }

            @Override
            public Form<? extends AbstractPostForm> visit(TextPost post) {
                return entityMapper.map(post, new Form<TextPostForm>() {});
            }
        });

        linkTo(methodOn(PostResource.class).update(post.getId(), new TextPostForm()))
                .build(resource::addLink);
        linkTo(methodOn(PostResource.class).delete(post.getId()))
                .build(resource::addLink);

        JsonObject owner = resource.find("owner", JsonObject.class);
        linkTo(methodOn(UserSelectResource.class).list(new UserSelectSearch()))
                .build(owner::addLink);

        JsonBoolean likes = resource.find("likes", JsonBoolean.class);
        linkTo(methodOn(LikesResource.class).like(post.getId()))
                .build(likes::addLink);
        linkTo(methodOn(LikesResource.class).dislike(post.getId()))
                .build(likes::addLink);
        LikesSearch likesSearch = new LikesSearch();
        likesSearch.setPost(id);
        linkTo(methodOn(LikesResource.class).list(likesSearch))
                .build(likes::addLink);

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
            public AbstractPost visit(VideoPostForm form) {
                return new VideoPost();
            }

            @Override
            public AbstractPost visit(ImagePostForm form) {
                return new ImagePost();
            }

            @Override
            public AbstractPost visit(TextPostForm form) {
                return new TextPost();
            }

        });


        post = post.accept(new AbstractPostVisitor<>() {
            @Override
            public AbstractPost visit(ImagePost post) {
                return restMapper.map(resource, ImagePost.class);
            }

            @Override
            public AbstractPost visit(VideoPost post) {
                return restMapper.map(resource, VideoPost.class);
            }

            @Override
            public AbstractPost visit(TextPost post) {
                return restMapper.map(resource, TextPost.class);
            }

        });

        for (User like : post.getLikes()) {
            if (! like.equals(identityManager.getUser())) {
                throw new WebApplicationException(Response.Status.CONFLICT);
            }
        }

        post.setOwner(identityManager.getUser());

        entityManager.persist(post);

        ResponseOk response = new ResponseOk();
        response.setId(post.getId());

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
            public AbstractPost visit(VideoPost post) {
                return  restMapper.map(resource, VideoPost.class);
            }

            @Override
            public AbstractPost visit(TextPost post) {
                return  restMapper.map(resource, TextPost.class);
            }
        });

        post.setOwner(identityManager.getUser());

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
    @RolesAllowed({"Administrator", "User"})
    @MethodPredicate(OwnerPostIdentity.class)
    @Produces(MediaType.APPLICATION_JSON)
    @LinkDescription("Delete Post")
    @DELETE
    public ResponseOk delete(@QueryParam("id") UUID id) {
        AbstractPost userPost = entityManager.getReference(AbstractPost.class, id);
        entityManager.remove(userPost);
        ResponseOk response = new ResponseOk();

        linkTo(methodOn(TimelineResource.class).list(new TimelineSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }
}
