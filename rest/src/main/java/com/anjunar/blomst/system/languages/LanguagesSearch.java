package com.anjunar.blomst.system.languages;

import com.anjunar.common.rest.search.AbstractRestSearch;
import com.anjunar.common.rest.search.RestSort;
import com.anjunar.common.rest.search.provider.GenericSortProvider;

import java.util.List;

public class LanguagesSearch extends AbstractRestSearch {

    @RestSort(GenericSortProvider.class)
    private List<String> sort;

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }
}
