package com.anjunar.common.rest.schema.annotations;

import com.anjunar.common.rest.schema.schema.JsonNode;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, TYPE})
@Retention(RUNTIME)
@Documented
public @interface JsonSchema {

    String title() default "";

    String description() default "";

    JsonNode.Widget widget() default JsonNode.Widget.NO_WIDGET;

    boolean id() default false;

    boolean naming() default false;

    boolean ignore() default false;

    boolean readOnly() default false;
}
