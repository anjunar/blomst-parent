package com.anjunar.blomst.social.pages.page.questions;

import com.anjunar.blomst.shared.likeable.AbstractLikeableSearch;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.api.jaxrs.RestPredicate;
import com.anjunar.common.rest.api.jaxrs.RestSort;
import com.anjunar.common.rest.api.jaxrs.provider.GenericSortProvider;

import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.UUID;

public class QuestionsSearch extends AbstractLikeableSearch {

    @QueryParam("sort")
    @RestSort(GenericSortProvider.class)
    private List<String> sort;

    @QueryParam("page")
    @RestPredicate(PageProvider.class)
    private UUID page;

    @QueryParam("topic")
    @RestPredicate(TopicProvider.class)
    private String topic;

    @QueryParam("editor")
    @RestPredicate(TextProvider.class)
    private String editor;

    @RestPredicate(OwnerProvider.class)
    private UserSelect owner;

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public UUID getPage() {
        return page;
    }

    public void setPage(UUID page) {
        this.page = page;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public UserSelect getOwner() {
        return owner;
    }

    public void setOwner(UserSelect owner) {
        this.owner = owner;
    }
}
