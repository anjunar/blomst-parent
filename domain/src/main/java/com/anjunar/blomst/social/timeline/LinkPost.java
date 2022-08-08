package com.anjunar.blomst.social.timeline;

import com.anjunar.common.filedisk.Image;
import org.hibernate.annotations.Filter;

import jakarta.persistence.*;
import java.net.URL;

@Entity
@Filter(name = "deletedFilter", condition = "deleted = false")
public class LinkPost extends AbstractPost {

    private URL link;

    private String title;

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    public URL getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public <E> E accept(AbstractPostVisitor<E> visitor) {
        return visitor.visit(this);
    }

    public void setLink(URL link) {
        this.link = link;
    }
}
