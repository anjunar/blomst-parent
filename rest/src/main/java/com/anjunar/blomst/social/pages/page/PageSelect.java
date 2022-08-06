package com.anjunar.blomst.social.pages.page;

import com.anjunar.blomst.social.pages.Page;
import com.anjunar.common.rest.api.AbstractRestEntity;

import java.util.Locale;

public class PageSelect extends AbstractRestEntity {

    private String title;

    private Locale language;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public static PageSelect factory(Page link) {
        PageSelect pageSelect = new PageSelect();
        pageSelect.setId(link.getId());
        pageSelect.setLanguage(link.getLanguage());
        pageSelect.setTitle(link.getTitle());
        return pageSelect;
    }

}
