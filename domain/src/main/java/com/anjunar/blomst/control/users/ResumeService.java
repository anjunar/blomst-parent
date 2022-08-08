package com.anjunar.blomst.control.users;

import com.anjunar.common.ddd.OnPersist;
import com.anjunar.common.security.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class ResumeService {

    public void onUserCreate(@Observes @OnPersist User user, EntityManager entityManager) {
        Resume resume = new Resume();
        resume.setOwner(user);
        entityManager.persist(resume);
    }

}
