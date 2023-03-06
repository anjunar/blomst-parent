package com.anjunar.blomst.social.pages;

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

public class PagesSearch extends AbstractLikeableSearch {

    @QueryParam("sort")
    @RestSort(GenericSortProvider.class)
    private List<String> sort;

    @QueryParam("title")
    @RestPredicate(GenericLikeProvider.class)
    private String title;

    @QueryParam("editor")
    @RestPredicate(GenericTextProvider.class)
    private String editor;

    @QueryParam("language")
    @RestPredicate(GenericManyToOneProvider.class)
    private UUID language;

    @QueryParam("modifier")
    @RestPredicate(GenericManyToOneProvider.class)
    private UUID modifier;

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public UUID getLanguage() {
        return language;
    }

    public void setLanguage(UUID language) {
        this.language = language;
    }

    public UUID getModifier() {
        return modifier;
    }

    public void setModifier(UUID modifier) {
        this.modifier = modifier;
    }
}
