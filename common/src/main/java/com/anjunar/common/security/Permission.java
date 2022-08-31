package com.anjunar.common.security;

import com.anjunar.common.ddd.AbstractEntity;
import org.hibernate.annotations.Filter;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Filter(name = "deletedFilter", condition = "deleted = false")
public class Permission extends AbstractEntity {

    private String url;

    @Size(min = 3)
    private String method;

    public String getUrl() {
        return url;
    }

    public void setUrl(String name) {
        this.url = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
