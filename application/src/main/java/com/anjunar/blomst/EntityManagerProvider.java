package com.anjunar.blomst;

import org.hibernate.Session;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Stateful
public class EntityManagerProvider {

    @Resource(lookup="java:jboss/datasources/anjunar")
    private DataSource dataSource;

    @PersistenceContext(unitName = "main")
    private EntityManager entityManager;

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
