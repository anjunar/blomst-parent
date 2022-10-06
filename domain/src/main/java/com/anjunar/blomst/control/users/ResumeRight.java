package com.anjunar.blomst.control.users;

import com.anjunar.common.ddd.AbstractRight;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class ResumeRight extends AbstractRight<Resume> {

    @ManyToOne
    private Resume source;

    @Override
    public Resume getSource() {
        return source;
    }

    @Override
    public void setSource(Resume source) {
        this.source = source;
    }
}
