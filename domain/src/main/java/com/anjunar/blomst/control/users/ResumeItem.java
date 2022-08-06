package com.anjunar.blomst.control.users;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.blomst.social.sites.Site;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Year;

@Entity
@Table(name = "do_resume_item")
public class ResumeItem extends AbstractEntity {

    @ManyToOne
    private Site site;

    @Column(name = "resume_start")
    private Year start;

    @Column(name = "resume_end")
    private Year end;

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
}
