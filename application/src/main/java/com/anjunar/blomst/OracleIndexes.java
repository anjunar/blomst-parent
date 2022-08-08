package com.anjunar.blomst;

import com.anjunar.common.ddd.OracleIndex;
import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import com.anjunar.introspector.type.TypeResolver;
import com.anjunar.introspector.type.resolved.ResolvedType;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;
import oracle.jdbc.pool.OracleDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class OracleIndexes {

    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) ServletContext init) throws SQLException {
/*
        DataSource dataSource = new OracleDataSource();
        ConnectionBuilder builder = dataSource.createConnectionBuilder();
        dataSource.setURL(props.getProperty("ORACLE_DB_URL"));
        dataSource.setUser(props.getProperty("ORACLE_DB_USERNAME"));
        dataSource.setPassword(props.getProperty("ORACLE_DB_PASSWORD"));
        List<ResolvedType<?>> entities = TypeResolver.filter(resolvedType -> resolvedType.getAnnotation(Entity.class) != null);

        for (ResolvedType<?> entity : entities) {
            BeanModel<?> entityModel = BeanIntrospector.create(entity);

            for (BeanProperty<?, ?> property : entityModel.getProperties()) {
                if (property.isDirect()) {
                    OracleIndex annotation = property.getAnnotation(OracleIndex.class);
                    if (annotation != null) {
                        Class<?> entityClass = entity.getRawType();
                        Table tableAnnotation = entityClass.getAnnotation(Table.class);
                        Column columnAnnotation = property.getAnnotation(Column.class);

                        String columnName;
                        if (columnAnnotation == null) {
                            columnName = property.getKey();
                        } else {
                            columnName = columnAnnotation.name();
                        }

                        String tableName;
                        if (tableAnnotation == null) {
                            tableName = entityClass.getSimpleName();
                        } else {
                            tableName = tableAnnotation.name();
                        }

                        String indexName = tableName + "_" + columnName + "_idx";

                        Connection connection = dataSource.getConnection();
                        Statement readStatement = connection.createStatement();
                        String queryString = "select * from USER_INDEXES where TABLE_NAME = '" + tableName.toUpperCase() + "' and INDEX_NAME = '" + indexName.toUpperCase() + "'";
                        ResultSet execute = readStatement.executeQuery(queryString);
                        if (! execute.next()) {
                            Statement writeStatement = connection.createStatement();
                            String createIndex = "CREATE INDEX " + indexName + " ON " + tableName + "(" + columnName + ") INDEXTYPE IS CTXSYS.CONTEXT PARAMETERS ('SYNC(ON COMMIT)')";
                            writeStatement.executeUpdate(createIndex);
                        }
                        execute.close();
                    }
                }
            }
        }
*/
    }
}
