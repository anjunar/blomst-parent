package com.anjunar.blomst;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateful;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.reactive.mutiny.Mutiny;
import org.hibernate.type.Type;

import javax.sql.DataSource;

@Stateful
public class EntityManagerProvider {

    @Resource(lookup="java:jboss/datasources/blomst")
    private DataSource dataSource;

    @PersistenceContext(unitName = "main")
    private EntityManager entityManager;

    @Produces
    public Mutiny.SessionFactory getMutiny() {
        EntityManagerFactory factory = entityManager.getEntityManagerFactory();
        return factory.unwrap(Mutiny.SessionFactory.class);
    }

    @Produces
    public EntityManager getEntityManager() {
        final Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedFilter");
        return entityManager;
    }

    @Produces
    public DataSource getDataSource() {
        return dataSource;
    }

}
