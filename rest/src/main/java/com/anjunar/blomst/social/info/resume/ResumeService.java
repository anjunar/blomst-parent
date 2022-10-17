package com.anjunar.blomst.social.info.resume;

import com.anjunar.blomst.control.users.Resume;
import com.anjunar.common.ddd.AbstractCriteriaSearchService;
import com.anjunar.common.security.IdentityManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class ResumeService extends AbstractCriteriaSearchService<Resume, ResumeSearch> {

    @Inject
    public ResumeService(EntityManager entityManager, IdentityManager identityManager) {
        super(entityManager, identityManager);
    }

    public ResumeService() {
        this(null, null);
    }

}
