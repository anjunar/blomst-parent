package com.anjunar.common.i18n;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.SqlTypes;
import org.hibernate.usertype.UserType;

import java.io.IOException;
import java.io.Serializable;
import java.sql.*;
import java.util.Locale;
import java.util.Map;

public class I18nType implements UserType<Map> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public int getSqlType() {
        return SqlTypes.JSON;
    }

    @Override
    public Class<Map> returnedClass() {
        return Map.class;
    }

    @Override
    public boolean equals(Map x, Map y) {
        return x.equals(y);
    }

    @Override
    public int hashCode(Map x) {
        return x.hashCode();
    }

    @Override
    public Map nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        try {
            String string = rs.getString(position);
            MapType mapType = objectMapper.getTypeFactory().constructMapType(Map.class, Locale.class, String.class);
            return objectMapper.readValue(string, mapType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Map value, int index, SharedSessionContractImplementor session) throws SQLException {
        try {
            String string = objectMapper.writeValueAsString(value);
/*
            PGobject jsonObject = new PGobject();
            jsonObject.setType("json");
            jsonObject.setValue(string);
            st.setObject(index, jsonObject);
*/
            st.setString(index, string);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map deepCopy(Map value) {
        return Map.copyOf(value);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Map value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map assemble(Serializable cached, Object owner) {
        try {
            MapType mapType = objectMapper.getTypeFactory().constructMapType(Map.class, Locale.class, String.class);
            return objectMapper.readValue((String) cached, mapType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map replace(Map detached, Map managed, Object owner) {
        return deepCopy(detached);
    }
}
