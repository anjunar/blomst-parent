package com.anjunar.common.rest.api;

import java.time.LocalDate;

public class DateDuration {

    private LocalDate from;

    private LocalDate to;

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }
}
