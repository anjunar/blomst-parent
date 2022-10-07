package com.anjunar.common.ddd;

import com.anjunar.common.security.Category;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
public abstract class AbstractRight<S extends AbstractEntity> extends AbstractEntity {

    @Column(name = "source_column")
    private String column;

    @ManyToMany
    private final Set<Category> categories = new HashSet<>();

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public abstract S getSource();

    public abstract void setSource(S source);
}
