package com.anjunar.blomst.social.timeline;

import com.anjunar.common.filedisk.Image;
import org.hibernate.annotations.Filter;

import jakarta.persistence.*;

@Entity
@Filter(name = "deletedFilter", condition = "deleted = false")
public class ImagePost extends AbstractPost {

    @ManyToOne(cascade = CascadeType.ALL)
    private Image image;

    @Override
    public <E> E accept(AbstractPostVisitor<E> visitor) {
        return visitor.visit(this);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
