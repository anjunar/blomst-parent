package com.anjunar.blomst.control.users;

import com.anjunar.common.ddd.AbstractRight;
import com.anjunar.common.security.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.util.HashSet;
import java.util.Set;

@Entity
public class ResumeRight extends AbstractRight<Resume> {

    @OneToOne
    private Resume source;

    @Override
    public Resume getSource() {
        return source;
    }

    public void setSource(Resume source) {
        this.source = source;
    }

}
