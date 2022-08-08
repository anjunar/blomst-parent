package com.anjunar.common.security;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.filedisk.Image;

import jakarta.persistence.*;

@Entity
@Table(name = "co_identity")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Identity extends AbstractEntity {

    private boolean enabled;

    @ManyToOne(cascade = CascadeType.ALL)
    private Image picture;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }
}
