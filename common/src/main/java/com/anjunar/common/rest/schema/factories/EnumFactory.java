package com.anjunar.common.rest.schema.factories;

import com.google.common.reflect.TypeToken;
import com.anjunar.common.rest.schema.schema.JsonEnum;
import com.anjunar.introspector.bean.BeanProperty;
import com.anjunar.introspector.type.TypeResolver;
import com.anjunar.introspector.type.resolved.ResolvedMethod;
import com.anjunar.introspector.type.resolved.ResolvedType;

@SuppressWarnings("UnstableApiUsage")
public class EnumFactory extends JsonAbstractFactory<JsonEnum> {

    @Override
    public boolean test(TypeToken<?> typeToken) {
        return typeToken.isSubtypeOf(Enum.class);
    }

    @Override
    public JsonEnum build(TypeToken<?> typeToken) {
        Enum[] enumConstants = (Enum[]) typeToken.getRawType().getEnumConstants();
        JsonEnum jsonEnum = new JsonEnum();
        for (Enum enumConstant : enumConstants) {
            ResolvedType<? extends Enum> resolvedType = TypeResolver.resolve(enumConstant.getClass());
            ResolvedMethod resolvedMethod = resolvedType.find("getValue");
            if (resolvedMethod == null) {
                jsonEnum.getEnums().add(enumConstant.name());
            } else {
                String value = (String) resolvedMethod.invoke(enumConstant);
                jsonEnum.getEnums().add(value);
            }
        }
        return jsonEnum;
    }

    @Override
    public JsonEnum buildWithAnnotation(BeanProperty<?, ?> property) {
        JsonEnum jsonEnum = super.buildWithAnnotation(property);
        return jsonEnum;
    }

}
