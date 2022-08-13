package com.anjunar.common.rest.schema;

import com.anjunar.common.rest.schema.factories.*;
import com.google.common.collect.Lists;

import java.util.List;

public class JsonRegistry {

    public static List<JsonAbstractFactory<?>> factories = Lists.newArrayList(
            new BigDecimalFactory(),
            new BigIntegerFactory(),
            new BooleanFactory(),
            new CollectionFactory(),
            new DoubleFactory(),
            new DurationFactory(),
            new EnumFactory(),
            new FloatFactory(),
            new ImageFactory(),
            new InstantFactory(),
            new IntegerFactory(),
            new LocalDateFactory(),
            new LocalDateTimeFactory(),
            new LocaleFactory(),
            new LongFactory(),
            new NumberFactory(),
            new StringFactory(),
            new UUIDFactory(),
            new URLFactory(),
            new BeanFactory()
    );

}
