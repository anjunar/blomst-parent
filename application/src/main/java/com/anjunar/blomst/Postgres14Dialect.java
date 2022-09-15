package com.anjunar.blomst;


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
                "levenshtein(?1, ?2)",
                typeConfiguration.getBasicTypeRegistry().resolve(StandardBasicTypes.INTEGER)
        );

        functionRegistry.registerPattern(
                "jsonPathAsJson",
                "?1::json -> ?2",
                typeConfiguration.getBasicTypeRegistry().resolve(StandardBasicTypes.STRING)
        );

        functionRegistry.registerPattern(
                "jsonPathAsText",
                "?1::json ->> ?2",
                typeConfiguration.getBasicTypeRegistry().resolve(StandardBasicTypes.STRING)
        );


        functionRegistry.registerPattern(
                "distance",
                "to_tsvector(?1) @@ to_tsquery(?2)",
                typeConfiguration.getBasicTypeRegistry().resolve(StandardBasicTypes.BOOLEAN)
        );


    }
}
