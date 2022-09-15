package com.anjunar.blomst.jaxrs;

import com.anjunar.common.rest.api.LongIntervall;
import jakarta.ws.rs.ext.ParamConverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberIntervallConverter implements ParamConverter<LongIntervall> {

    Pattern pattern = Pattern.compile("from(.*)to(.*)");

    @Override
    public LongIntervall fromString(String value) {
        Matcher matcher = pattern.matcher(value);
        if (matcher.matches()) {
            LongIntervall duration = new LongIntervall();
            String from = matcher.group(1);
            String to = matcher.group(2);
            duration.setFrom(Long.valueOf(from));
            duration.setTo(Long.valueOf(to));
            return duration;
        }
        return null;
    }

    @Override
    public String toString(LongIntervall value) {
        return "from" + value.getFrom().toString() + "to" + value.getTo().toString();
    }
}
