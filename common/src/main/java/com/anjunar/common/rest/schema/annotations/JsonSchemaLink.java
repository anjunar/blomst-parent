package com.anjunar.common.rest.schema.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, TYPE})
@Retention(RUNTIME)
@Documented
public @interface JsonSchemaLink {

    Class<?> resource();

    String method();

}
