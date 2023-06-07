package com.anjunar.blomst.shared.alternatives.alternative;

import com.anjunar.blomst.shared.Alternative;
import com.anjunar.blomst.social.sites.Site;
import com.anjunar.common.rest.api.Form;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.security.IdentityStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.Path;

import java.util.UUID;

@ApplicationScoped
@Path("social/sites/site/alternatives/alternative")
public class AlternativeResource implements FormResourceTemplate<AlternativeForm> {

    private final EntityManager entityManager;

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;

    private final IdentityStore identityStore;

    @Inject
    public AlternativeResource(EntityManager entityManager, ResourceEntityMapper entityMapper, ResourceRestMapper restMapper, IdentityStore identityStore) {
        this.entityManager = entityManager;
        this.entityMapper = entityMapper;
        this.restMapper = restMapper;
        this.identityStore = identityStore;
    }

    public AlternativeResource() {
        this(null, null, null, null);
    }

    @Override
    public Form<AlternativeForm> read(UUID id) {

        Alternative entity = entityManager.find(Alternative.class, id);

        AlternativeForm resource = entityMapper.map(entity, AlternativeForm.class);

        return new Form<>(resource) {};
    }

    @Override
    public AlternativeForm save(AlternativeForm form) {

        Alternative entity = restMapper.map(form, Alternative.class);
        entity.setValue(form.getValue());
        entity.setProperty(form.getProperty());
        entity.setEntity(Site.class.getSimpleName());
        entity.setOwner(identityStore.getUser());

        entityManager.persist(entity);

        form.setId(entity.getId());

        return form;
    }

    @Override
    public AlternativeForm update(UUID id, AlternativeForm form) {

        Alternative entity = restMapper.map(form, Alternative.class);

        entity.setValue(form.getValue());
        entity.setProperty(form.getProperty());
        entity.setEntity(Site.class.getSimpleName());
        entity.setOwner(identityStore.getUser());

        return form;
    }

    @Override
    public ResponseOk delete(UUID id) {
        Alternative entity = entityManager.getReference(Alternative.class, id);
        entityManager.remove(entity);
        return new ResponseOk();
    }

}
