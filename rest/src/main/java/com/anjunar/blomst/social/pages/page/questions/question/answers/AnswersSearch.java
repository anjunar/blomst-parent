package com.anjunar.blomst.social.pages.page.questions.question.answers;

import com.anjunar.blomst.shared.likeable.AbstractLikeableSearch;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.search.RestPredicate;
import com.anjunar.common.rest.search.RestSort;
import com.anjunar.common.rest.search.provider.GenericManyToOneProvider;
import com.anjunar.common.rest.search.provider.GenericSortProvider;

import com.anjunar.common.rest.search.provider.GenericTextProvider;
import jakarta.ws.rs.QueryParam;
import java.util.List;
import java.util.UUID;

public class AnswersSearch extends AbstractLikeableSearch {

    @QueryParam("sort")
    @RestSort(GenericSortProvider.class)
    private List<String> sort;

    @QueryParam("question")
    @RestPredicate(GenericManyToOneProvider.class)
    private UUID question;

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

    public UUID getQuestion() {
        return question;
    }

    public void setQuestion(UUID question) {
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
