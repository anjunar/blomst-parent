package com.anjunar.blomst;


import org.hibernate.dialect.Oracle12cDialect;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.query.spi.QueryEngine;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.spi.TypeConfiguration;

public class Postgres14Dialect extends PostgreSQLDialect {

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
