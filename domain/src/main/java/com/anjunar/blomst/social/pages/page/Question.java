package com.anjunar.blomst.social.pages.page;

import com.anjunar.common.ddd.OracleIndex;
import com.anjunar.common.security.User;
import com.anjunar.blomst.shared.Likeable;
import com.anjunar.blomst.social.pages.Page;
import org.hibernate.annotations.Filter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "do_question")
@Filter(name = "deletedFilter", condition = "deleted = false")
public class Question extends Likeable {

    @Lob
    @OracleIndex(type = OracleIndex.Type.TEXT)
    private String topic;

    @Lob
    private String html;

    @Lob
    @OracleIndex(type = OracleIndex.Type.TEXT)
    private String text;

    @ManyToOne
    private User owner;

    @ManyToOne
    private Page page;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private final List<Answer> replies = new ArrayList<>();

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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
