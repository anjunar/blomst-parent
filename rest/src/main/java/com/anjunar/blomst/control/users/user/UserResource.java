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
import com.anjunar.common.filedisk.Image;
import com.anjunar.common.rest.MethodPredicate;
import com.anjunar.common.rest.MyOwnIdentity;
import com.anjunar.common.rest.api.AbstractSchemaEntity;
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
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
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
public class UserResource implements FormResourceTemplate<Form<UserForm>> {

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

    @Override
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Read User")
    public Form<UserForm> read(UUID id) {

        User user = entityManager.find(User.class, id);

        Form<UserForm> resource = entityMapper.map(user, new Form<>() {});

        linkTo(methodOn(UserResource.class).update(id, (Form<UserForm>) null))
                .build(resource::addLink);
        linkTo(methodOn(UserResource.class).delete(id))
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

        linkTo(methodOn(LoginResource.class).runAs(id))
                .build(resource::addLink);
        linkTo(methodOn(ApplicationResource.class).validate(null))
                .build(resource::addLink);

        ResumeSearch resumeSearch = new ResumeSearch();
        resumeSearch.setOwner(id);
        linkTo(methodOn(ResumeResource.class).list(resumeSearch))
                .withRel("resume")
                .build(resource::addLink);

        AddressesSearch addressesSearch = new AddressesSearch();
        addressesSearch.setOwner(id);
        linkTo(methodOn(AddressesResource.class).list(addressesSearch))
                .withRel("addresses")
                .build(resource::addLink);

        try {
            UserConnection connection = service.findConnection(identityManager.getUser().getId(), id);
            linkTo(methodOn(UserConnectionResource.class).read(connection.getId()))
                    .withRel("connection")
                    .build(resource::addLink);
        } catch (NoResultException e) {
            if (!identityManager.getUser().getId().equals(id)) {
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
                .withRel("site-connections")
                .build(resource::addLink);

        return resource;
    }

    @Override
    @RolesAllowed("Administrator")
    @LinkDescription("Save User")
    public ResponseOk save(Form<UserForm> resource) {

        User user = restMapper.map(resource, User.class);

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

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(UsersResource.class).list(new UsersSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Update User")
    @MethodPredicate(MyOwnIdentity.class)
    public ResponseOk update(UUID id, Form<UserForm> resource) {

        User user = restMapper.map(resource, User.class);

        service.confirmationEmail(user, request);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(UsersResource.class).list(new UsersSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
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
