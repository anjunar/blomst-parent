package com.anjunar.blomst.social.sites;

import com.anjunar.blomst.social.sites.site.SiteForm;
import com.anjunar.blomst.social.sites.site.SiteResource;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;

import com.anjunar.common.rest.objectmapper.ResourceMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.link.WebURLBuilderFactory.methodOn;

@Path("social/sites")
@ApplicationScoped
public class SitesResource implements ListResourceTemplate<SiteForm, SitesSearch> {

    private final SitesService service;

    @Inject
    public SitesResource(SitesService service) {
        this.service = service;
    }

    public SitesResource() {
        this(null);
    }

    @Transactional
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Table Site")
    @Override
    public Table<SiteForm> list(SitesSearch search) {
        final long count = service.count(search);
        final List<Site> sites = service.find(search);
        final List<SiteForm> resources = new ArrayList<>();

        for (Site site : sites) {
            ResourceMapper mapper = new ResourceMapper();
            SiteForm form = mapper.map(site, SiteForm.class);

            linkTo(methodOn(SiteResource.class).read(site.getId()))
                    .build(form::addLink);

            resources.add(form);
        }

        Table<SiteForm> table = new Table<>(resources, count) {};

        linkTo(methodOn(SiteResource.class).create())
                .build(table::addLink);

        return table;
    }

}
