package com.anjunar.blomst.social.timeline;

import com.anjunar.common.ddd.OracleIndex;
import com.anjunar.common.security.Identity;
import com.anjunar.common.security.User;
import com.anjunar.blomst.shared.Likeable;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractPost extends Likeable {

    @Lob
    @OracleIndex(type = OracleIndex.Type.TEXT)
    private String text;

    @ManyToOne
    private User owner;

    @ManyToOne
    private Identity source;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private final List<Comment> comments = new ArrayList<>();

    public abstract <E> E accept(AbstractPostVisitor<E> visitor);

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Identity getSource() {
        return source;
    }

    public void setSource(Identity source) {
        this.source = source;
    }

    public List<Comment> getComments() {
        return comments;
    }

}
