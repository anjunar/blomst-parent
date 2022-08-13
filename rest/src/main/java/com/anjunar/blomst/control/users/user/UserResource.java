package com.anjunar.blomst.control.users.user;

import com.anjunar.blomst.control.users.user.connections.UserConnectionsResource;
import com.anjunar.blomst.control.users.user.connections.UserConnectionsSearch;
import com.anjunar.blomst.control.users.user.connections.connection.UserConnectionResource;
import com.anjunar.blomst.security.login.LoginResource;
import com.anjunar.blomst.security.register.RegisterResource;
import com.google.common.collect.Sets;
import com.anjunar.common.filedisk.Image;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.MethodPredicate;
import com.anjunar.common.rest.SelfIdentity;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.schema.schema.JsonArray;
import com.anjunar.common.security.EmailType;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.User;
import com.anjunar.blomst.ApplicationResource;
import com.anjunar.blomst.control.roles.RolesResource;
import com.anjunar.blomst.control.roles.RolesSearch;
import com.anjunar.blomst.control.users.Resume;
import com.anjunar.blomst.control.users.UserConnection;
import com.anjunar.blomst.social.communities.community.connections.CommunityConnectionsResource;
import com.anjunar.blomst.social.communities.community.connections.CommunityConnectionsSearch;
import com.anjunar.blomst.social.info.resume.ResumeResource;
import com.anjunar.blomst.social.sites.site.connections.SiteConnectionsResource;
import com.anjunar.blomst.social.sites.site.connections.SiteConnectionsSearch;
import com.anjunar.blomst.social.timeline.TimelineResource;
import com.anjunar.blomst.social.timeline.TimelineSearch;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.link.WebURLBuilderFactory.methodOn;

@RequestScoped
@Path("control/users/user")
public class UserResource implements FormResourceTemplate<UserForm> {

    private static final Logger log = LoggerFactory.getLogger(RegisterResource.class);

    private final EntityManager entityManager;

    private final IdentityProvider identityProvider;

    private final HttpServletRequest request;

    private final UserService service;

    @Inject
    public UserResource(EntityManager entityManager, IdentityProvider identityProvider, @Context HttpServletRequest request, UserService service) {
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;
        this.request = request;
        this.service = service;
    }

    public UserResource() {
        this(null, null, null, null);
    }

    @Path("confirm")
    @GET
    @RolesAllowed({"Administrator", "User"})
    @Produces("application/json")
    @Transactional
    @LinkDescription("Confirm Email")
    public ResponseOk confirm(@QueryParam("id") UUID id, @QueryParam("hash") String hash) {
        User user = entityManager.find(User.class, id);

        for (EmailType email : user.getEmails()) {
            if (email.getHash().equals(hash)) {
                email.setConfirmed(true);
                return new ResponseOk();
            }
        }

        throw new WebApplicationException(jakarta.ws.rs.core.Response.Status.FORBIDDEN);
    }

    @Transactional
    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed("Administrator")
    @LinkDescription("Create User")
    public UserForm create() {
        UserForm resource = new UserForm();

        JsonArray roles = resource.find("roles", JsonArray.class);
        linkTo(methodOn(RolesResource.class).list(null))
                .build(roles::addLink);
        linkTo(methodOn(ApplicationResource.class).validate(null))
                .build(resource::addLink);
        linkTo(methodOn(UserResource.class).save(null))
                .build(resource::addLink);

        return resource;
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Read User")
    public UserForm read(UUID id) {

        User user = entityManager.find(User.class, id);

        UserForm resource = UserForm.factory(user);

        if (identityProvider.hasRole("Administrator") || identityProvider.getUser().equals(user)) {
            resource.setPassword(user.getPassword());
        }

        Resume resume = service.findResume(identityProvider.getUser());
        linkTo(methodOn(ResumeResource.class).read(resume.getId()))
                .withRel("resume")
                .build(resource::addLink);

        try {
            UserConnection connection = service.findConnection(identityProvider.getUser().getId(), id);
            linkTo(methodOn(UserConnectionResource.class).read(connection.getId()))
                    .withRel("connection")
                    .build(resource::addLink);
        } catch (NoResultException e) {
            if (! identityProvider.getUser().getId().equals(id)) {
                linkTo(methodOn(UserConnectionResource.class).create(id))
                        .withRel("connect")
                        .build(resource::addLink);
            }
        }

        TimelineSearch timelineSearch = new TimelineSearch();
        timelineSearch.setSource(Sets.newHashSet(id));
        linkTo(methodOn(TimelineResource.class).list(timelineSearch))
                .withRel("timeline")
                .build(resource::addLink);

        linkTo(methodOn(UserResource.class).update(id, null))
                .build(resource::addLink);
        linkTo(methodOn(UserResource.class).delete(id))
                .build(resource::addLink);

        JsonArray roles = resource.find("roles", JsonArray.class);
        linkTo(methodOn(RolesResource.class).list(new RolesSearch()))
                .build(roles::addLink);
        linkTo(methodOn(LoginResource.class).runAs(id))
                .build(resource::addLink);
        linkTo(methodOn(ApplicationResource.class).validate(null))
                .build(resource::addLink);

        UserConnectionsSearch userConnectionsSearch = new UserConnectionsSearch();
        userConnectionsSearch.setFrom(id);
        linkTo(methodOn(UserConnectionsResource.class).list(userConnectionsSearch))
                .withRel("user-connections")
                .build(resource::addLink);

        CommunityConnectionsSearch communityConnectionsSearch = new CommunityConnectionsSearch();
        communityConnectionsSearch.setFrom(id);
        linkTo(methodOn(CommunityConnectionsResource.class).list(communityConnectionsSearch))
                .withRel("community-connections")
                .build(resource::addLink);

        SiteConnectionsSearch siteConnectionsSearch = new SiteConnectionsSearch();
        siteConnectionsSearch.setFrom(id);
        linkTo(methodOn(SiteConnectionsResource.class).list(siteConnectionsSearch))
                .withRel("site-connection")
                .build(resource::addLink);

        return resource;
    }

    @Override
    @Transactional
    @RolesAllowed("Administrator")
    @LinkDescription("Save User")
    public UserForm save(UserForm resource) {

        User user = new User();

        UserForm.updater(resource, user, entityManager, identityProvider);

        user.setPassword(resource.getPassword());

        service.confirmationEmail(user, request);

        try {
            if (user.getPicture() == null) {
                URL picture = getClass()
                        .getClassLoader()
                        .getResource("META-INF/resources/user.png");
                InputStream inputStream = picture.openStream();
                byte[] bytes = new byte[inputStream.available()];
                IOUtils.readFully(inputStream, bytes);
                Image image = new Image();
                image.setName("user.png");
                image.setData(bytes);
                image.setLastModified(LocalDateTime.now());
                image.setType("image");
                image.setSubType("png");
                user.setPicture(image);
            }
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }


        entityManager.persist(user);

        resource.setId(user.getId());

        linkTo(methodOn(UserResource.class).update(user.getId(), null))
                .build(resource::addLink);
        linkTo(methodOn(UserResource.class).delete(user.getId()))
                .build(resource::addLink);

        return resource;
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Update User")
    @MethodPredicate(SelfIdentity.class)
    public UserForm update(UUID id, UserForm resource) {
        User user = entityManager.find(User.class, id);

        UserForm.updater(resource, user, entityManager, identityProvider);

        if (identityProvider.hasRole("Administrator") || identityProvider.getUser().equals(user)) {
            user.setPassword(resource.getPassword());
        }

        service.confirmationEmail(user, request);

        linkTo(methodOn(UserResource.class).update(id, null))
                .build(resource::addLink);
        linkTo(methodOn(UserResource.class).delete(id))
                .build(resource::addLink);

        return resource;
    }

    @Override
    @Transactional
    @RolesAllowed({"Administrator", "User"})
    @MethodPredicate(SelfIdentity.class)
    @LinkDescription("Delete User")
    public ResponseOk delete(UUID id) {
        User user = entityManager.find(User.class, id);

        entityManager.remove(user);

        return new ResponseOk();
    }

}
