package com.anjunar.blomst;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerFactoryProducer {

    public static final String TEST = "main";

    @Produces
    @ApplicationScoped
    public EntityManagerFactory create() {
        return Persistence.createEntityManagerFactory(TEST);
    }

    public void destroy(@Disposes EntityManagerFactory factory) {
        factory.close();
    }

}
