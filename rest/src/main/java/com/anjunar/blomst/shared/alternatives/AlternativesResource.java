package com.anjunar.blomst.shared.alternatives;

import com.anjunar.blomst.shared.Alternative;
import com.anjunar.blomst.shared.alternatives.alternative.AlternativeForm;
import com.anjunar.blomst.shared.alternatives.alternative.AlternativeResource;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.link.WebURLBuilderFactory;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.security.IdentityStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;


@ApplicationScoped
@Path("social/sites/site/alternatives")
public class AlternativesResource implements ListResourceTemplate<AlternativeForm, AlternativesSearch> {

    private final AlternativesService service;

    private final ResourceEntityMapper entityMapper;

    private final EntityManager entityManager;

    private final IdentityStore identityStore;

    @Inject
    public AlternativesResource(AlternativesService service, ResourceEntityMapper entityMapper, EntityManager entityManager, IdentityStore identityStore) {
        this.service = service;
        this.entityMapper = entityMapper;
        this.entityManager = entityManager;
        this.identityStore = identityStore;
    }

    public AlternativesResource() {
        this(null, null, null, null);
    }

    @Override
    public Table<AlternativeForm> list(AlternativesSearch search) {
        long count = service.count(search);
        List<Alternative> forms = service.find(search);
        List<AlternativeForm> formList = new ArrayList<>();

        List<AlternativeGroupBy> resultList = entityManager.createQuery("select new com.anjunar.blomst.shared.alternatives.AlternativeGroupBy(count(a), a.value) from Alternative a where a.property = :property and a.entity = :entity group by a.value", AlternativeGroupBy.class)
                .setParameter("property", search.getProperty())
                .setParameter("entity", search.getEntity())
                .getResultList();

        Map<String, Long> counts = new HashMap<>();
        for (AlternativeGroupBy test : resultList) {
            counts.put(test.getValue(), test.getCount());
        }

        for (Alternative form : forms) {
            AlternativeForm resource = entityMapper.map(form, AlternativeForm.class);
            resource.setCount(counts.get(form.getValue()));

            if (form.getOwner().equals(identityStore.getUser())) {
                linkTo(methodOn(AlternativeResource.class).delete(form.getId()))
                        .build(resource::addLink);
            }

            formList.add(resource);
        }

        return new Table<>(formList, count) {};
    }
}
