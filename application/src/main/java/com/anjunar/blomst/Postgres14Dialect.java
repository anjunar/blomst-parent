package com.anjunar.blomst;


import org.hibernate.dialect.Oracle12cDialect;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.query.spi.QueryEngine;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.spi.TypeConfiguration;

import java.sql.Types;

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
                "distance",
                "?1 @@ to_tsquery(?2, ?3);",
                typeConfiguration.getBasicTypeRegistry().resolve(StandardBasicTypes.BOOLEAN)
        );


    }
}
