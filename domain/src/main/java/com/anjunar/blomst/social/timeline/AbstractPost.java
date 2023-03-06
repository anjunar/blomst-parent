package com.anjunar.blomst.social.timeline;

import com.anjunar.blomst.shared.Likeable;
import com.anjunar.blomst.social.pages.Editor;
import com.anjunar.common.ddd.PostgresIndex;
import com.anjunar.common.i18n.Detector;
import com.anjunar.common.security.Identity;
import com.anjunar.common.security.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractPost extends Likeable {

    @Embedded
    private Editor editor;

    @ManyToOne
    private User owner;

    @ManyToOne
    private Identity source;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private final List<Comment> comments = new ArrayList<>();

    public abstract <E> E accept(AbstractPostVisitor<E> visitor);

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
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
