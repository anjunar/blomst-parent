package com.anjunar.blomst;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class EntityManagerProducer {

    @Inject
    private EntityManagerFactory entityManagerFactory;

    @Produces
    public EntityManager create() {
        return entityManagerFactory.createEntityManager();
    }

    public void destroy(@Disposes EntityManager em) {
        em.close();
    }

}
