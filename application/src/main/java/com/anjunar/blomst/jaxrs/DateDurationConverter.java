package com.anjunar.blomst.jaxrs;

import com.anjunar.common.rest.api.DateDuration;
import com.anjunar.common.rest.api.DateTimeDuration;
import jakarta.ws.rs.ext.ParamConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateDurationConverter implements ParamConverter<DateDuration> {

    Pattern pattern = Pattern.compile("from(.*)to(.*)");

    @Override
    public DateDuration fromString(String value) {
        Matcher matcher = pattern.matcher(value);
        if (matcher.matches()) {
            DateDuration duration = new DateDuration();
            String from = matcher.group(1);
            String to = matcher.group(2);
            duration.setFrom(LocalDate.parse(from));
            duration.setTo(LocalDate.parse(to));
            return duration;
        }
        return null;
    }

    @Override
    public String toString(DateDuration value) {
        return "from" + value.getFrom().toString() + "to" + value.getTo().toString();
    }
}
