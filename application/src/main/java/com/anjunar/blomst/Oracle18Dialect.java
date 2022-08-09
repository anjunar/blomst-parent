package com.anjunar.blomst;


import org.hibernate.dialect.Oracle12cDialect;

public class Oracle18Dialect extends Oracle12cDialect {

    public Oracle18Dialect() {
//        registerFunction("levenshtein", new OracleSQLLevenstheinFunction());
//        registerFunction("contains", new OracleSQLContainsFunction());
    }

}
