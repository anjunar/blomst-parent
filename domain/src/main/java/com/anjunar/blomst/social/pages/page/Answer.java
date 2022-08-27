package com.anjunar.blomst.social.pages.page;

import com.anjunar.blomst.social.pages.Editor;
import com.anjunar.common.ddd.OracleIndex;
import com.anjunar.common.security.User;
import com.anjunar.blomst.shared.Likeable;
import org.hibernate.annotations.Filter;

import jakarta.persistence.*;

@Entity
@Table(name = "do_answer")
@Filter(name = "deletedFilter", condition = "deleted = false")
public class Answer extends Likeable {

    @Embedded
    private Editor editor;

    @ManyToOne
    private User owner;

    @ManyToOne
    private Question question;

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

    public Question getTopic() {
        return question;
    }

    public void setTopic(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

}
