package com.anjunar.blomst.social.sites.site;

import com.anjunar.blomst.social.sites.SiteConnection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.UUID;

@ApplicationScoped
public class SiteService {

    private final EntityManager entityManager;

    @Inject
    public SiteService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public SiteService() {
        this(null);
    }

    public SiteConnection findConnection(UUID from, UUID to) {
        return entityManager.createQuery("select s from SiteConnection s where s.from.id = :from and s.to.id = :to", SiteConnection.class)
                .setParameter("from", from)
                .setParameter("to", to)
                .getSingleResult();
    }
}
