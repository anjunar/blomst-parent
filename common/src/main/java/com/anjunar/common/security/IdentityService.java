package com.anjunar.common.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.time.LocalDate;
import java.util.UUID;

@ApplicationScoped
public class IdentityService {

    private final EntityManager entityManager;

    @Inject
    public IdentityService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public IdentityService() {
        this(null);
    }

    public void saveUser(User entity) {
        entityManager.persist(entity);
    }

    public User updateUser(User entity) {
        return entityManager.merge(entity);
    }

    public void deleteUser(UUID id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    public User findUser(UUID primaryKey) {
        return entityManager.find(User.class, primaryKey);
    }

    public boolean authenticate(User user) {
        return entityManager
                .createQuery("select count(e) from User e where e.firstName = :firstName and e.lastName = :lastName and e.birthDate = :birthDate and e.password = :password", Long.class)
                .setParameter("firstName", user.getFirstName())
                .setParameter("lastName", user.getLastName())
                .setParameter("birthDate", user.getBirthDate())
                .setParameter("password", user.getPassword())
                .getSingleResult() > 0;
    }

    public Role findGroup(String name) {
        return entityManager.createQuery("select (e) from Role e where e.name = :name", Role.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public void saveRole(Role role) {
        entityManager.persist(role);
    }

    public User findUser(String firstName, String lastName, LocalDate birthDate) {
        try {
            return entityManager
                    .createQuery("select e from User e where e.firstName = :firstName and e.lastName = :lastName and e.birthDate = :birthDate", User.class)
                    .setParameter("firstName", firstName)
                    .setParameter("lastName", lastName)
                    .setParameter("birthDate", birthDate)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Permission findPermission(String url, String httpMethod) {
        try {
            return entityManager.createQuery("select p from Permission p where p.url = :url and p.method = :method", Permission.class)
                    .setParameter("url", url)
                    .setParameter("method", httpMethod)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void savePermission(Permission permission) {
        entityManager.persist(permission);
    }

    public User findUserByToken(String token) {
        try {
            return entityManager.createQuery("select u from User u where u.token = :token", User.class)
                    .setParameter("token", token)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Category findCategory(String name) {
        try {
            return entityManager.createQuery("select c from Category c where c.name = :name", Category.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void saveCategory(Category category) {
        entityManager.persist(category);
    }
}
