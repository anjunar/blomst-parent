package com.anjunar.blomst.shared.alternatives;

public class AlternativeGroupBy {

    private final Long count;

    private final String value;

    public AlternativeGroupBy(Long count, String value) {
        this.count = count;
        this.value = value;
    }

    public Long getCount() {
        return count;
    }

    public String getValue() {
        return value;
    }

    }
