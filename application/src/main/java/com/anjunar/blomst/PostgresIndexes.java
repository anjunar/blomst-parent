package com.anjunar.blomst;

import com.anjunar.common.ddd.PostgresIndex;
import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import com.anjunar.introspector.type.TypeResolver;
import com.anjunar.introspector.type.resolved.ResolvedType;
import com.google.common.base.Strings;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

/**
 * CREATE EXTENSION IF NOT EXISTS fuzzystrmatch with schema pg_catalog;
 *
 * CREATE EXTENSION IF NOT EXISTS btree_gin with schema pg_catalog;
 *
 * ALTER TABLE page ADD COLUMN document tsvector GENERATED ALWAYS AS (to_tsvector('german', coalesce(text, ''))) STORED;
 *
 * CREATE INDEX ts_idx ON page USING gin(text);
 *
 * SELECT * FROM page where page.text @@ to_tsquery('german', 'Transzendenz');
 *
 * explain analyse
 *     SELECT * FROM page where page.text @@ to_tsquery('german', 'Transzendenz');
 *
 * CREATE FUNCTION to_reg_config(text) RETURNS regconfig AS 'select $1::regconfig' LANGUAGE SQL IMMUTABLE;
 *
 * CREATE INDEX test_index ON page USING gin(to_tsvector(to_reg_config(language), text));
 */
public class PostgresIndexes {

    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) ServletContext init, DataSource dataSource) throws SQLException {

        List<ResolvedType<?>> entities = TypeResolver.filter(resolvedType -> resolvedType.getAnnotation(Entity.class) != null);

        for (ResolvedType<?> entity : entities) {
            BeanModel<?> entityModel = BeanIntrospector.create(entity);
            Class<?> entityClass = entity.getRawType();
            Table tableAnnotation = entityClass.getAnnotation(Table.class);

            for (BeanProperty<?, ?> property : entityModel.getProperties()) {
                if (property.isDirect()) {
                    PostgresIndex annotation = property.getAnnotation(PostgresIndex.class);
                    if (annotation != null) {
                        Column columnAnnotation = property.getAnnotation(Column.class);
                        extracted(dataSource, property, entityClass, tableAnnotation, columnAnnotation);
                    }

                    Embedded embedded = property.getAnnotation(Embedded.class);
                    if (Objects.nonNull(embedded)) {
                        Class<?> embeddedType = property.getType().getRawType();
                        BeanModel<?> embeddedModel = BeanIntrospector.create(embeddedType);
                        for (BeanProperty<?, ?> embeddedProperty : embeddedModel.getProperties()) {
                            if (embeddedProperty.isDirect()) {
                                PostgresIndex postgresIndex = embeddedProperty.getAnnotation(PostgresIndex.class);
                                if (postgresIndex != null) {
                                    Column embeddedColumnAnnotation = embeddedProperty.getAnnotation(Column.class);
                                    extracted(dataSource, embeddedProperty, entityClass, tableAnnotation, embeddedColumnAnnotation);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void extracted(DataSource dataSource, BeanProperty<?, ?> property, Class<?> entityClass, Table tableAnnotation, Column columnAnnotation) throws SQLException {
        String columnName;
        if (columnAnnotation == null) {
            columnName = property.getKey();
        } else {
            if (Strings.isNullOrEmpty(columnAnnotation.name())) {
                columnName = convert(property.getKey());
            } else {
                columnName = columnAnnotation.name();
            }
        }

        String tableName;
        if (tableAnnotation == null) {
            tableName = convert(entityClass.getSimpleName());
        } else {
            if (Strings.isNullOrEmpty(tableAnnotation.name())) {
                tableName = entityClass.getSimpleName();
            } else {
                tableName = tableAnnotation.name();
            }
        }

        String indexName = tableName + "_" + columnName + "_idx";

        Connection connection = dataSource.getConnection();
        Statement readStatement = connection.createStatement();
        String queryString = "select * from pg_indexes where schemaname = 'public' and INDEXNAME = '" + indexName.toLowerCase() + "'";
        ResultSet execute = readStatement.executeQuery(queryString);
        if (! execute.next()) {
            Statement writeStatement = connection.createStatement();
            // CREATE INDEX ts_idx ON page USING gin(text);
            String createIndex = "CREATE INDEX " + indexName + " ON " + tableName + " USING gin(to_tsvector(to_reg_config(language), " + columnName + "));";
            writeStatement.execute(createIndex);
        }
        execute.close();
    }

    public static String convert(String value) {
        return value;
    }

/*
    public static String convert(String value) {
        final String regex = "([a-z])([A-Z])";
        final String replacement = "$1_$2";
        final String newName = value
                .replaceAll(regex, replacement)
                .toLowerCase();
        return newName;
    }
*/
}
