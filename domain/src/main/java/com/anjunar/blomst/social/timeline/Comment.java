package com.anjunar.blomst.social.timeline;

import com.anjunar.common.ddd.OracleIndex;
import com.anjunar.common.security.User;
import com.anjunar.blomst.shared.Likeable;
import org.hibernate.annotations.Filter;

import javax.persistence.*;

@Entity
@Table(name = "do_comment")
@Filter(name = "deletedFilter", condition = "deleted = false")
public class Comment extends Likeable {

    @Lob
    @OracleIndex(type = OracleIndex.Type.TEXT)
    private String text;

    @ManyToOne
    private AbstractPost post;

    @ManyToOne
    private User owner;

    @ManyToOne
    private Comment parent;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AbstractPost getPost() {
        return post;
    }

    public void setPost(AbstractPost post) {
        this.post = post;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }
}
