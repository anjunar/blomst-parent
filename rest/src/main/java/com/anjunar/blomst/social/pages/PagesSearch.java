package com.anjunar.blomst.social.pages;

import com.anjunar.blomst.shared.likeable.AbstractLikeableSearch;
import com.anjunar.blomst.shared.system.Language;
import com.anjunar.common.rest.search.RestPredicate;
import com.anjunar.common.rest.search.RestSort;
import com.anjunar.common.rest.search.provider.GenericSortProvider;

import java.util.List;

public class PagesSearch extends AbstractLikeableSearch {

    @RestSort(GenericSortProvider.class)
    private List<String> sort;

    @RestPredicate(TitleProvider.class)
    private String title;

    @RestPredicate(WordProvider.class)
    private String text;

    @RestPredicate(LanguageProvider.class)
    private Language language;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
