package com.anjunar.blomst.social.pages.page.questions.question.answers;

import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.control.users.user.UserSelect;
import com.anjunar.blomst.shared.likeable.AbstractLikeableSearch;
import com.anjunar.common.rest.mapper.annotations.MapperProjection;
import com.anjunar.common.rest.search.RestPredicate;
import com.anjunar.common.rest.search.RestSort;
import com.anjunar.common.rest.search.provider.GenericSortProvider;

import jakarta.ws.rs.QueryParam;
import java.util.List;
import java.util.UUID;

public class AnswersSearch extends AbstractLikeableSearch {

    @QueryParam("sort")
    @RestSort(GenericSortProvider.class)
    private List<String> sort;

    @QueryParam("topic")
    @RestPredicate(TopicProvider.class)
    private UUID topic;

    @QueryParam("editor")
    @RestPredicate(EditorProvider.class)
    private String editor;

    @RestPredicate(UserProvider.class)
    @MapperProjection(UserSelect.class)
    private UserForm owner;

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public UUID getTopic() {
        return topic;
    }

    public void setTopic(UUID topic) {
        this.topic = topic;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public UserForm getOwner() {
        return owner;
    }

    public void setOwner(UserForm owner) {
        this.owner = owner;
    }

}
