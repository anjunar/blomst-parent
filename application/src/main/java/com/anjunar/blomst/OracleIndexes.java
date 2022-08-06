package com.anjunar.blomst;

import com.anjunar.common.ddd.OracleIndex;
import de.anjunar.introspector.bean.BeanIntrospector;
import de.anjunar.introspector.bean.BeanModel;
import de.anjunar.introspector.bean.BeanProperty;
import de.anjunar.introspector.type.TypeResolver;
import de.anjunar.introspector.type.resolved.ResolvedType;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class OracleIndexes {

    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) ServletContext init, DataSource dataSource) throws SQLException {

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
    }
}
