package com.anjunar.blomst.social.sites.site;

import com.anjunar.blomst.shared.Alternative;
import com.anjunar.blomst.shared.alternatives.AlternativeGroupBy;
import com.anjunar.blomst.shared.alternatives.AlternativesResource;
import com.anjunar.blomst.shared.alternatives.AlternativesSearch;
import com.anjunar.blomst.shared.alternatives.alternative.AlternativeForm;
import com.anjunar.blomst.shared.alternatives.alternative.AlternativeResource;
import com.anjunar.blomst.social.info.addresses.AddressSearchResource;
import com.anjunar.blomst.social.sites.SitesResource;
import com.anjunar.blomst.social.sites.SitesSearch;
import com.anjunar.blomst.social.sites.site.connections.SiteConnectionsResource;
import com.anjunar.blomst.social.sites.site.connections.SiteConnectionsSearch;
import com.anjunar.blomst.social.sites.site.connections.connection.SiteConnectionResource;
import com.anjunar.blomst.social.timeline.TimelineResource;
import com.anjunar.blomst.social.timeline.TimelineSearch;
import com.anjunar.common.rest.api.Form;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.google.common.collect.Sets;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.blomst.social.sites.Site;
import com.anjunar.blomst.social.sites.SiteConnection;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@Path("social/sites/site")
@ApplicationScoped
public class SiteResource implements FormResourceTemplate<SiteForm> {

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    private final SiteService service;

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;


    @Inject
    public SiteResource(EntityManager entityManager, IdentityManager identityManager, SiteService service, ResourceEntityMapper entityMapper, ResourceRestMapper restMapper) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
        this.service = service;
        this.entityMapper = entityMapper;
        this.restMapper = restMapper;
    }

    public SiteResource() {
        this(null, null, null, null, null);
    }

    @Produces("application/json")
    @GET
    @Path("create")
    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Create Site")
    public Form<SiteForm> create() {
        final SiteForm resource = new SiteForm();
        Form<SiteForm> form = new Form<>(resource) {};

        linkTo(methodOn(SiteResource.class).save(new SiteForm()))
                .build(form::addLink);

        JsonObject address = form.find("address", JsonObject.class);
        linkTo(methodOn(AddressSearchResource.class).list(null))
                .build(address::addLink);

        JsonObject name = form.find("name", JsonObject.class);
        AlternativesSearch nameSearch = new AlternativesSearch();
        nameSearch.setProperty("name");
        nameSearch.setEntity("Site");
        linkTo(methodOn(AlternativesResource.class).list(nameSearch))
                .build(name::addLink);
        linkTo(methodOn(AlternativeResource.class).save(new AlternativeForm()))
                .build(name::addLink);
        JsonObject phone = form.find("phone", JsonObject.class);
        AlternativesSearch phoneSearch = new AlternativesSearch();
        phoneSearch.setProperty("phone");
        phoneSearch.setEntity("Site");
        linkTo(methodOn(AlternativesResource.class).list(phoneSearch))
                .build(phone::addLink);
        linkTo(methodOn(AlternativeResource.class).save(new AlternativeForm()))
                .build(phone::addLink);
        JsonObject homepage = form.find("homepage", JsonObject.class);
        AlternativesSearch homepageSearch = new AlternativesSearch();
        homepageSearch.setProperty("homepage");
        homepageSearch.setEntity("Site");
        linkTo(methodOn(AlternativesResource.class).list(homepageSearch))
                .build(homepage::addLink);
        linkTo(methodOn(AlternativeResource.class).save(new AlternativeForm()))
                .build(homepage::addLink);


        return form;
    }

    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Read Site")
    @Override
    public Form<SiteForm> read(UUID id) {
        final Site entity = entityManager.find(Site.class, id);

        Form<SiteForm> form = entityMapper.map(entity, new Form<>() {});

        JsonObject name = form.find("name", JsonObject.class);
        AlternativesSearch nameSearch = new AlternativesSearch();
        nameSearch.setProperty("name");
        nameSearch.setEntity("Site");
        linkTo(methodOn(AlternativesResource.class).list(nameSearch))
                .build(name::addLink);
        linkTo(methodOn(AlternativeResource.class).save(new AlternativeForm()))
                .build(name::addLink);
        JsonObject phone = form.find("phone", JsonObject.class);
        AlternativesSearch phoneSearch = new AlternativesSearch();
        phoneSearch.setProperty("phone");
        phoneSearch.setEntity("Site");
        linkTo(methodOn(AlternativesResource.class).list(phoneSearch))
                .build(phone::addLink);
        linkTo(methodOn(AlternativeResource.class).save(new AlternativeForm()))
                .build(phone::addLink);
        JsonObject homepage = form.find("homepage", JsonObject.class);
        AlternativesSearch homepageSearch = new AlternativesSearch();
        homepageSearch.setProperty("homepage");
        homepageSearch.setEntity("Site");
        linkTo(methodOn(AlternativesResource.class).list(homepageSearch))
                .build(homepage::addLink);
        linkTo(methodOn(AlternativeResource.class).save(new AlternativeForm()))
                .build(homepage::addLink);


        JsonObject address = form.find("address", JsonObject.class);
        linkTo(methodOn(AddressSearchResource.class).list(null))
                .build(address::addLink);

        try {
            SiteConnection connection = service.findConnection(identityManager.getUser().getId(), id);
            linkTo(methodOn(SiteConnectionResource.class).read(connection.getId()))
                    .withRel("connection")
                    .build(form::addLink);
        } catch (NoResultException e) {
            linkTo(methodOn(SiteConnectionResource.class).create(id))
                    .withRel("connect")
                    .build(form::addLink);
        }

        TimelineSearch timelineSearch = new TimelineSearch();
        timelineSearch.setSource(Sets.newHashSet(id));
        linkTo(methodOn(TimelineResource.class).list(timelineSearch))
                .withRel("timeline")
                .build(form::addLink);

        SiteConnectionsSearch siteConnectionsSearch = new SiteConnectionsSearch();
        siteConnectionsSearch.setTo(id);
        linkTo(methodOn(SiteConnectionsResource.class).list(siteConnectionsSearch))
                .withRel("connections")
                .build(form::addLink);

        linkTo(methodOn(SiteResource.class).update(id, new SiteForm()))
                .build(form::addLink);
        linkTo(methodOn(SiteResource.class).delete(id))
                .build(form::addLink);

        return form;
    }

    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Save Site")
    @Override
    public ResponseOk save(SiteForm form) {
        Site entity = restMapper.map(form, Site.class);

        entity.getName().setOwner(identityManager.getUser());
        entity.getHomepage().setOwner(identityManager.getUser());
        entity.getPhone().setOwner(identityManager.getUser());

        entityManager.persist(entity);

        ResponseOk response = new ResponseOk();
        response.setId(entity.getId());

        linkTo(methodOn(SitesResource.class).list(new SitesSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    private Alternative top1(String property, String entity) {
        String sql = "select * from Alternative c where c.value in( " +
                         "SELECT a.value " +
                            "FROM Alternative a where a.property = :property and a.entity = :entity " +
                            "GROUP BY a.value " +
                            "HAVING COUNT (a.value)=( " +
                                "SELECT MAX(bcount) " +
                                "FROM ( SELECT b.value, COUNT(b.value) bcount " +
                                            "FROM Alternative b where b.property = :property and b.entity = :entity " +
                                            "GROUP BY b.value) as vm)" +
                       ")";
        return (Alternative) entityManager.createNativeQuery(sql, Alternative.class)
                .setParameter("property", property)
                .setParameter("entity", entity)
                .setMaxResults(1)
                .getSingleResult();
    }

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Update Site")
    @Override
    public ResponseOk update(UUID id, SiteForm form) {

        Site entity = restMapper.map(form, Site.class);

        Alternative name = top1("name", "Site");

        Alternative phone = top1("phone", "Site");

        Alternative homepage = top1("homepage", "Site");

        entity.setName(name);
        entity.setPhone(phone);
        entity.setHomepage(homepage);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(SitesResource.class).list(new SitesSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @RolesAllowed({"Administrator", "User"})
    @LinkDescription("Delete Site")
    @Override
    public ResponseOk delete(UUID id) {
        Site entity = entityManager.getReference(Site.class, id);

        entityManager.remove(entity);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(SitesResource.class).list(new SitesSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

}
