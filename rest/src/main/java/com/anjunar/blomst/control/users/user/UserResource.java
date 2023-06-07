package com.anjunar.blomst.control.users.user;

import com.anjunar.blomst.ApplicationResource;
import com.anjunar.blomst.control.roles.RolesResource;
import com.anjunar.blomst.control.roles.RolesSearch;
import com.anjunar.blomst.control.users.UsersResource;
import com.anjunar.blomst.control.users.UsersSearch;
import com.anjunar.blomst.control.users.user.connections.UserConnectionsResource;
import com.anjunar.blomst.control.users.user.connections.UserConnectionsSearch;
import com.anjunar.blomst.control.users.user.connections.connection.UserConnectionResource;
import com.anjunar.blomst.security.login.LoginResource;
import com.anjunar.blomst.security.register.RegisterResource;
import com.anjunar.blomst.social.communities.community.connections.CommunityConnectionsResource;
import com.anjunar.blomst.social.communities.community.connections.CommunityConnectionsSearch;
import com.anjunar.blomst.social.info.addresses.AddressesResource;
import com.anjunar.blomst.social.info.addresses.AddressesSearch;
import com.anjunar.blomst.social.info.resume.ResumeResource;
import com.anjunar.blomst.social.info.resume.ResumeSearch;
import com.anjunar.blomst.social.sites.site.connections.SiteConnectionsResource;
import com.anjunar.blomst.social.sites.site.connections.SiteConnectionsSearch;
import com.anjunar.blomst.social.timeline.TimelineResource;
import com.anjunar.blomst.social.timeline.TimelineSearch;
import com.anjunar.blomst.system.languages.LanguagesResource;
import com.anjunar.blomst.system.languages.LanguagesSearch;
import com.anjunar.common.filedisk.Media;
import com.anjunar.common.rest.MethodPredicate;
import com.anjunar.common.rest.MyOwnIdentity;
import com.anjunar.common.rest.api.Form;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.rest.schema.schema.JsonArray;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.EmailType;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User;
import com.anjunar.common.security.UserConnection;
import com.google.common.collect.Sets;
import com.timgroup.jgravatar.Gravatar;
import com.timgroup.jgravatar.GravatarDefaultImage;
import com.timgroup.jgravatar.GravatarRating;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final IdentityManager identityManager;

    private final HttpServletRequest request;

    private final UserService service;

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;


    @Inject
    public UserResource(EntityManager entityManager, IdentityManager identityManager, @Context HttpServletRequest request, UserService service, ResourceEntityMapper entityMapper, ResourceRestMapper restMapper) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
        this.request = request;
        this.service = service;
        this.entityMapper = entityMapper;
        this.restMapper = restMapper;
    }

    public UserResource() {
        this(null, null, null, null, null, null);
    }

    @Path("confirm")
    @GET
    @RolesAllowed({"Administrator", "User"})
    @Produces("application/json")
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

    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed("Administrator")
    @LinkDescription("Create User")
    public Form<UserForm> create() {
        UserForm resource = new UserForm();
        Form<UserForm> form = new Form<>(resource) {};

        JsonArray roles = form.find("roles", JsonArray.class);
        linkTo(methodOn(RolesResource.class).list(null))
                .build(roles::addLink);
        JsonObject language = form.find("language", JsonObject.class);
        linkTo(methodOn(LanguagesResource.class).list(new LanguagesSearch()))
                .build(language::addLink);
        linkTo(methodOn(ApplicationResource.class).validate(null))
                .build(form::addLink);
        linkTo(methodOn(UserResource.class).save(null))
                .build(form::addLink);

        return form;
    }

    @GET
    @Path("{nickname}")
    @Produces("application/json")
    public Form<UserForm> read(@PathParam("nickname") String nickname) {
        try {
            User user = entityManager.createQuery("select u from User u where lower(u.nickName) = :nickname", User.class)
                    .setParameter("nickname", nickname.toLowerCase())
                    .getSingleResult();
            return getUser(user);
        } catch (NoResultException e) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Override
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Read User")
    public Form<UserForm> read(UUID id) {

        User user = entityManager.find(User.class, id);

        return getUser(user);
    }

    private Form<UserForm> getUser(User user) {
        Form<UserForm> resource = entityMapper.map(user, new Form<>() {});

        linkTo(methodOn(UserResource.class).update(user.getId(), new UserForm()))
                .build(resource::addLink);
        linkTo(methodOn(UserResource.class).delete(user.getId()))
                .build(resource::addLink);

        JsonArray roles = resource.find("roles", JsonArray.class);
        if (roles != null) {
            linkTo(methodOn(RolesResource.class).list(new RolesSearch()))
                    .build(roles::addLink);
        }
        JsonObject language = resource.find("language", JsonObject.class);
        if (language != null) {
            linkTo(methodOn(LanguagesResource.class).list(new LanguagesSearch()))
                    .build(language::addLink);
        }

        linkTo(methodOn(LoginResource.class).runAs(user.getId()))
                .build(resource::addLink);
        linkTo(methodOn(ApplicationResource.class).validate(null))
                .build(resource::addLink);

        ResumeSearch resumeSearch = new ResumeSearch();
        resumeSearch.setOwner(user.getId());
        linkTo(methodOn(ResumeResource.class).list(resumeSearch))
                .withRel("resume")
                .build(resource::addLink);

        AddressesSearch addressesSearch = new AddressesSearch();
        addressesSearch.setOwner(user.getId());
        linkTo(methodOn(AddressesResource.class).list(addressesSearch))
                .withRel("addresses")
                .build(resource::addLink);

        try {
            UserConnection connection = service.findConnection(identityManager.getUser().getId(), user.getId());
            linkTo(methodOn(UserConnectionResource.class).read(connection.getId()))
                    .withRel("connection")
                    .build(resource::addLink);
        } catch (NoResultException e) {
            if (!identityManager.getUser().getId().equals(user.getId())) {
                linkTo(methodOn(UserConnectionResource.class).create(user.getId()))
                        .withRel("connect")
                        .build(resource::addLink);
            }
        }

        TimelineSearch timelineSearch = new TimelineSearch();
        timelineSearch.setSource(Sets.newHashSet(user.getId()));
        linkTo(methodOn(TimelineResource.class).list(timelineSearch))
                .withRel("timeline")
                .build(resource::addLink);

        UserConnectionsSearch userConnectionsSearch = new UserConnectionsSearch();
        userConnectionsSearch.setFrom(user.getId());
        linkTo(methodOn(UserConnectionsResource.class).list(userConnectionsSearch))
                .withRel("user-connections")
                .build(resource::addLink);

        CommunityConnectionsSearch communityConnectionsSearch = new CommunityConnectionsSearch();
        communityConnectionsSearch.setFrom(user.getId());
        linkTo(methodOn(CommunityConnectionsResource.class).list(communityConnectionsSearch))
                .withRel("community-connections")
                .build(resource::addLink);

        SiteConnectionsSearch siteConnectionsSearch = new SiteConnectionsSearch();
        siteConnectionsSearch.setFrom(user.getId());
        linkTo(methodOn(SiteConnectionsResource.class).list(siteConnectionsSearch))
                .withRel("site-connections")
                .build(resource::addLink);

        return resource;
    }

    @Override
    @RolesAllowed("Administrator")
    @LinkDescription("Save User")
    public UserForm save(UserForm resource) {

        User user = restMapper.map(resource, User.class);

        service.confirmationEmail(user, request);

        Gravatar gravatar = new Gravatar()
                .setSize(50)
                .setRating(GravatarRating.GENERAL_AUDIENCES)
                .setDefaultImage(GravatarDefaultImage.IDENTICON);
        byte[] jpg = gravatar.download(resource.getEmails().get(0).getValue());

        Media picture = new Media();
        picture.setData(jpg);
        picture.setType("image");
        picture.setSubType("jpg");
        picture.setName("gravatar.jpg");
        user.setPicture(picture);

        Media thumbNail = new Media();
        thumbNail.setData(jpg);
        thumbNail.setType("image");
        thumbNail.setSubType("jpg");
        thumbNail.setName("gravatar.jpg");
        picture.setThumbnail(thumbNail);


        entityManager.persist(user);
        resource.setId(user.getId());

        linkTo(methodOn(UsersResource.class).list(new UsersSearch()))
                .withRel("redirect")
                .build(resource::addLink);

        return resource;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Update User")
    @MethodPredicate(MyOwnIdentity.class)
    public UserForm update(UUID id, UserForm resource) {

        User user = restMapper.map(resource, User.class);

        service.confirmationEmail(user, request);

        linkTo(methodOn(UsersResource.class).list(new UsersSearch()))
                .withRel("redirect")
                .build(resource::addLink);

        return resource;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @MethodPredicate(MyOwnIdentity.class)
    @LinkDescription("Delete User")
    public ResponseOk delete(UUID id) {
        User user = entityManager.find(User.class, id);

        entityManager.remove(user);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(UsersResource.class).list(new UsersSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

}
