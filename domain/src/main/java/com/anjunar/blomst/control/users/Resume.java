package com.anjunar.blomst.control.users;

import com.anjunar.blomst.social.sites.Site;
import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.security.User;
import jakarta.persistence.*;

import java.time.Year;

@Entity
public class Resume extends AbstractEntity {

    @ManyToOne
    private User owner;

    @ManyToOne
    private Site site;

    @Column(name = "resume_start")
    private Year start;

    @Column(name = "resume_end")
    private Year end;

    @OneToOne(mappedBy = "source", cascade = CascadeType.ALL)
    private ResumeRight right;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Year getStart() {
        return start;
    }

    public void setStart(Year start) {
        this.start = start;
    }

    public Year getEnd() {
        return end;
    }

    public void setEnd(Year end) {
        this.end = end;
    }

    public ResumeRight getRight() {
        return right;
    }
    public void setRight(ResumeRight right) {
        this.right = right;
    }
}
