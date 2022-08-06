package com.anjunar.blomst;

import com.anjunar.blomst.sqlfunction.PostgreSQLFTSFunction;
import com.anjunar.blomst.sqlfunction.PostgreSQLLevenshteinFunction;
import org.hibernate.dialect.PostgreSQL9Dialect;

public class CustomPostgreSQLDialect extends PostgreSQL9Dialect {

    public CustomPostgreSQLDialect () {
        registerFunction("levenshtein", new PostgreSQLLevenshteinFunction());
        registerFunction("fts", new PostgreSQLFTSFunction());
    }

}