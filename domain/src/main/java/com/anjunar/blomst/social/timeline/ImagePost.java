package com.anjunar.blomst.social.timeline;

import com.anjunar.common.filedisk.Media;
import org.hibernate.annotations.Filter;

import jakarta.persistence.*;

@Entity
@Filter(name = "deletedFilter", condition = "deleted = false")
public class ImagePost extends AbstractPost {

    @ManyToOne(cascade = CascadeType.ALL)
    private Media media;

    @Override
    public <E> E accept(AbstractPostVisitor<E> visitor) {
        return visitor.visit(this);
    }

    public Media getImage() {
        return media;
    }

    public void setImage(Media media) {
        this.media = media;
    }

}
