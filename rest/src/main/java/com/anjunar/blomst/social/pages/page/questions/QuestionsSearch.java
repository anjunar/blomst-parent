package com.anjunar.blomst.social.pages.page.questions;

import com.anjunar.blomst.shared.likes.likeable.AbstractLikeableSearch;
import com.anjunar.common.rest.search.RestPredicate;
import com.anjunar.common.rest.search.RestSort;
import com.anjunar.common.rest.search.provider.GenericLikeProvider;
import com.anjunar.common.rest.search.provider.GenericManyToOneProvider;
import com.anjunar.common.rest.search.provider.GenericSortProvider;

import com.anjunar.common.rest.search.provider.GenericTextProvider;
import jakarta.ws.rs.QueryParam;
import java.util.List;
import java.util.UUID;

public class QuestionsSearch extends AbstractLikeableSearch {

    @QueryParam("sort")
    @RestSort(GenericSortProvider.class)
    private List<String> sort;

    @QueryParam("page")
    @RestPredicate(GenericManyToOneProvider.class)
    private UUID page;

    @QueryParam("topic")
    @RestPredicate(GenericLikeProvider.class)
    private String question;

    @QueryParam("editor")
    @RestPredicate(GenericTextProvider.class)
    private String editor;

    @QueryParam("owner")
    @RestPredicate(GenericManyToOneProvider.class)
    private UUID owner;

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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }
}
