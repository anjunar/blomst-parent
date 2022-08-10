package com.anjunar.blomst;


import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.dialect.Oracle12cDialect;
import org.hibernate.query.spi.QueryEngine;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.spi.TypeConfiguration;

public class Oracle18Dialect extends Oracle12cDialect {

    @Override
    public void initializeFunctionRegistry(QueryEngine queryEngine) {
        super.initializeFunctionRegistry(queryEngine);

        TypeConfiguration typeConfiguration = queryEngine.getTypeConfiguration();
        SqmFunctionRegistry functionRegistry = queryEngine.getSqmFunctionRegistry();

        functionRegistry.registerPattern(
                "levensthein",
                "UTL_MATCH.EDIT_DISTANCE(?1, ?2)",
                typeConfiguration.getBasicTypeRegistry().resolve(StandardBasicTypes.INTEGER)
        );

        functionRegistry.registerPattern(
                "contains",
                "contains(?1, ?2, 1)",
                typeConfiguration.getBasicTypeRegistry().resolve(StandardBasicTypes.INTEGER)
        );

    }
}
