package com.anjunar.common.ddd;

import com.anjunar.common.security.Category;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;

import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
public abstract class AbstractRight<S> extends AbstractEntity {
    @ManyToMany
    private Set<Category> categories = new HashSet<>();

    public Set<Category> getCategories() {
        return categories;
    }

    public abstract S getSource();

    abstract public void setSource(S categories);

}
