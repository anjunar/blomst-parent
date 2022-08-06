### Oracle Database

Login as SYS AS SYSDBA

```sql
alter session set "_ORACLE_SCRIPT"=true;
CREATE USER anjunar IDENTIFIED BY anjunar;
GRANT ALL PRIVILEGES TO anjunar;

CREATE INDEX post_text_idx ON DO_POST(TEXT) INDEXTYPE IS CTXSYS.CONTEXT PARAMETERS ('SYNC(ON COMMIT)');
CREATE INDEX comment_text_idx ON DO_COMMENT(TEXT) INDEXTYPE IS CTXSYS.CONTEXT PARAMETERS ('SYNC(ON COMMIT)');
CREATE INDEX page_text_idx ON DO_PAGE(TEXT) INDEXTYPE IS CTXSYS.CONTEXT PARAMETERS ('SYNC(ON COMMIT)');
CREATE INDEX question1_topic_idx ON DO_QUESTION(TOPIC) INDEXTYPE IS CTXSYS.CONTEXT PARAMETERS ('SYNC(ON COMMIT)');
CREATE INDEX question2_text_idx ON DO_QUESTION(TEXT) INDEXTYPE IS CTXSYS.CONTEXT PARAMETERS ('SYNC(ON COMMIT)');
CREATE INDEX answer_text_idx ON DO_ANSWER(TEXT) INDEXTYPE IS CTXSYS.CONTEXT PARAMETERS ('SYNC(ON COMMIT)');
```

### Wildfly standalone.xml

```xml
        <subsystem xmlns="urn:jboss:domain:datasources:6.0">
            <datasources>
                <datasource jndi-name="java:jboss/datasources/ExampleDS" pool-name="ExampleDS" enabled="true" use-java-context="true">
                    <connection-url>jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE</connection-url>
                    <driver>h2</driver>
                    <security>
                        <user-name>sa</user-name>
                        <password>sa</password>
                    </security>
                </datasource>
                <datasource jndi-name="java:jboss/datasources/anjunar" pool-name="Anjunar" enabled="true" use-java-context="true">
                    <connection-url>jdbc:oracle:thin:@localhost:1521:orcl</connection-url>
                    <driver>oracle</driver>
                    <security>
                        <user-name>anjunar</user-name>
                        <password>anjunar</password>
                    </security>
                </datasource>
                <drivers>
                    <driver name="h2" module="com.h2database.h2">
                        <xa-datasource-class>org.h2.jdbcx.JdbcDataSource</xa-datasource-class>
                    </driver>
                    <driver name="mysql" module="com.mysql.jdbc">
                        <xa-datasource-class>com.mysql.jdbc.jdbc2.optional.MysqlXADataSource</xa-datasource-class>
                    </driver>
                    <driver name="postgresql" module="org.postgresql">
                        <driver-class>org.postgresql.Driver</driver-class>
                    </driver>
                    <driver name="oracle" module="com.oracle">
                        <driver-class>oracle.jdbc.driver.OracleDriver</driver-class>
                    </driver>
                    <driver name="p6spy" module="com.p6spy">
                        <driver-class>com.p6spy.engine.spy.P6SpyDriver</driver-class>
                    </driver>
                </drivers>
            </datasources>
        </subsystem>
```

```xml
        <subsystem xmlns="urn:jboss:domain:mail:4.0">
            <mail-session name="default" jndi-name="java:jboss/mail/Default">
                <smtp-server outbound-socket-binding-ref="mail-smtp" ssl="false" tls="true" username="anjunar@gmx.de" password="s3cr3t"/>
            </mail-session>
        </subsystem>
```


```xml
        <outbound-socket-binding name="mail-smtp">
            <remote-destination host="mail.gmx.net" port="587"/>
        </outbound-socket-binding>
```


configure-elytron.cli
```
# CLI script to enable elytron for the quickstart application in the application server

# Enable a default JACC policy with WildFly Elytron
/subsystem=elytron/policy=jacc:add(jacc-policy={})

# Disable 'integrated-jaspi' as the quickstart will be managing it's own identities
/subsystem=undertow/application-security-domain=other:write-attribute(name=integrated-jaspi, value=false)

# Reload the server configuration
reload
```

jboss-cli.bat --connect --file=configure-elytron.cli

### WildFly Modules containing the Database Drivers

anjunar-parent/application/src/main/server/wildFly/modules

copy to $JBOSS_HOME/modules