package com.anjunar.blomst.control.users;

import com.anjunar.common.ddd.OnPersist;
import com.anjunar.common.security.User;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;

@ApplicationScoped
public class ResumeService {

    public void onUserCreate(@Observes @OnPersist User user, EntityManager entityManager) {
        Resume resume = new Resume();
        resume.setOwner(user);
        entityManager.persist(resume);
    }

}
