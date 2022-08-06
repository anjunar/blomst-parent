package com.anjunar.blomst.social.pages.page;

import com.anjunar.common.ddd.OracleIndex;
import com.anjunar.common.security.User;
import com.anjunar.blomst.shared.Likeable;
import org.hibernate.annotations.Filter;

import javax.persistence.*;

@Entity
@Table(name = "do_answer")
@Filter(name = "deletedFilter", condition = "deleted = false")
public class Answer extends Likeable {

    @Lob
    @OracleIndex(type = OracleIndex.Type.TEXT)
    private String text;

    @Lob
    private String html;

    @ManyToOne
    private User owner;

    @ManyToOne
    private Question question;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

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
