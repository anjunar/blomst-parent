package com.anjunar.blomst.social.timeline;

import com.anjunar.common.filedisk.Media;
import org.hibernate.annotations.Filter;

import jakarta.persistence.*;

@Entity
@Filter(name = "deletedFilter", condition = "deleted = false")
public class VideoPost extends AbstractPost {

    @ManyToOne(cascade = CascadeType.ALL)
    private Media video;

    public Media getVideo() {
        return video;
    }

    public void setVideo(Media video) {
        this.video = video;
    }

    @Override
    public <E> E accept(AbstractPostVisitor<E> visitor) {
        return visitor.visit(this);
    }

}
