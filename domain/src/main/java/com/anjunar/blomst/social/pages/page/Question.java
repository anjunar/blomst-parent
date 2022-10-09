package com.anjunar.blomst.social.pages.page;

import com.anjunar.blomst.shared.Likeable;
import com.anjunar.blomst.social.pages.Editor;
import com.anjunar.blomst.social.pages.Page;
import com.anjunar.common.security.User;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Filter(name = "deletedFilter", condition = "deleted = false")
public class Question extends Likeable {

    @Column(columnDefinition = "TEXT")
    private String topic;

    @Embedded
    private Editor editor;

    @ManyToOne
    private User owner;

    @ManyToOne
    private Page page;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private final List<Answer> replies = new ArrayList<>();

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

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

    public List<Answer> getReplies() {
        return replies;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

}
