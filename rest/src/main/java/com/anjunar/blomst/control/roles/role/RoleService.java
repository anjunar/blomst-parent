package com.anjunar.blomst.control.roles.role;

import com.anjunar.common.security.Role;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RoleService {

    private final EntityManager entityManager;

    @Inject
    public RoleService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public RoleService() {
        this(null);
    }

    public void saveRole(Object entity) {
        entityManager.persist(entity);
    }

    public Role updateRole(Role entity) {
        return entityManager.merge(entity);
    }

    public void delete(Object entity) {
        entityManager.remove(entity);
    }

    public Role findRole(Object primaryKey) {
        return entityManager.find(Role.class, primaryKey);
    }
}
