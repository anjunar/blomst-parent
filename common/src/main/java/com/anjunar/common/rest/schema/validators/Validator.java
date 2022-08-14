package com.anjunar.common.rest.schema.validators;

import com.anjunar.common.rest.schema.schema.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(DecimalMaxValidator.class),
        @JsonSubTypes.Type(DecimalMinValidator.class),
        @JsonSubTypes.Type(DigitsValidator.class),
        @JsonSubTypes.Type(EmailValidator.class),
        @JsonSubTypes.Type(FutureOrPresentValidator.class),
        @JsonSubTypes.Type(FutureValidator.class),
        @JsonSubTypes.Type(MaxValidator.class),
        @JsonSubTypes.Type(MinValidator.class),
        @JsonSubTypes.Type(NegativeOrZeroValidator.class),
        @JsonSubTypes.Type(NegativeValidator.class),
        @JsonSubTypes.Type(NotBlankValidator.class),
        @JsonSubTypes.Type(NotNullValidator.class),
        @JsonSubTypes.Type(PastOrPresentValidator.class),
        @JsonSubTypes.Type(PastValidator.class),
        @JsonSubTypes.Type(PatternValidator.class),
        @JsonSubTypes.Type(PositiveOrZeroValidator.class),
        @JsonSubTypes.Type(SizeValidator.class),
})
public interface Validator {

    String getName();

}
