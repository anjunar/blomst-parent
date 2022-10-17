package com.anjunar.blomst.control.users;

import com.anjunar.common.ddd.AbstractRight;
import com.anjunar.common.security.User;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class ResumeRight extends AbstractRight<Resume> {

    @OneToOne(cascade = CascadeType.ALL)
    private Resume source;

    @Override
    public Resume getSource() {
        return source;
    }

    public void setSource(Resume source) {
        this.source = source;
    }

}
