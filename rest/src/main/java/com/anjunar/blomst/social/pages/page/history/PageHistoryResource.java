package com.anjunar.blomst.social.pages.page.history;

import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.blomst.social.pages.Page;
import com.anjunar.blomst.social.pages.page.PageResource;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.WebURLBuilderFactory.methodOn;

@ApplicationScoped
@Path("pages/page/history")
public class PageHistoryResource implements ListResourceTemplate<PageHistoryForm, PageHistorySearch> {

    private final EntityManager entityManager;

    @Inject
    public PageHistoryResource(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public PageHistoryResource() {
        this(null);
    }

    @Override
    @RolesAllowed({"Administrator", "User", "Guest"})
    @Transactional
    @LinkDescription("Table Page History")
    public Table<PageHistoryForm> list(PageHistorySearch search) {

        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        List<Number> revisions = auditReader.getRevisions(Page.class, search.getId());
        List<PageHistoryForm> resources = new ArrayList<>();
        List<Number> pages = revisions.subList(search.getIndex(), search.getIndex() + search.getLimit());

        for (Number revision : pages) {
            Page page = auditReader.find(Page.class, search.getId(), revision);

            PageHistoryForm resource = PageHistoryForm.factory(page, revision);

            linkTo(methodOn(PageResource.class).read(page.getId(), (Integer) revision))
                    .build(resource::addLink);

            resources.add(resource);
        }

        return new Table<>(resources, revisions.size()) {};
    }
}
