package com.anjunar.blomst.jaxrs;

import com.anjunar.common.rest.api.DateTimeDuration;

import jakarta.ws.rs.ext.ParamConverter;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DurationConverter implements ParamConverter<DateTimeDuration> {

    Pattern pattern = Pattern.compile("from(.*)to(.*)");

    @Override
    public DateTimeDuration fromString(String value) {
        Matcher matcher = pattern.matcher(value);
        if (matcher.matches()) {
            DateTimeDuration duration = new DateTimeDuration();
            String from = matcher.group(1);
            String to = matcher.group(2);
            duration.setFrom(LocalDateTime.parse(from));
            duration.setTo(LocalDateTime.parse(to));
            return duration;
        }
        return null;
    }

    @Override
    public String toString(DateTimeDuration value) {
        return "from" + value.getFrom().toString() + "to" + value.getTo().toString();
    }
}
