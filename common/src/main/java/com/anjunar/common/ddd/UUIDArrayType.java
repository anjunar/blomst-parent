package com.anjunar.common.ddd;

import org.apache.commons.lang3.SerializationUtils;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.SqlTypes;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UUIDArrayType implements UserType<ArrayList> {

    @Override
    public int getSqlType() {
        return SqlTypes.CHAR;
    }

    @Override
    public Class<ArrayList> returnedClass() {
        return ArrayList.class;
    }

    @Override
    public boolean equals(ArrayList x, ArrayList y) {
        return x.equals(y);
    }

    @Override
    public int hashCode(ArrayList x) {
        return x.hashCode();
    }

    @Override
    public ArrayList nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        Pattern pattern = Pattern.compile("\\{(.*)\\}");
        Matcher matcher = pattern.matcher(rs.getString(position));
        List<UUID> uuids = new ArrayList<>();
        if (matcher.matches()) {
            String id = matcher.group(1);
            String[] ids = id.split(",");
            for (String uuidString : ids) {
                UUID uuid = UUID.fromString(uuidString);
                uuids.add(uuid);
            }
        }
        return (ArrayList) uuids;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, ArrayList value, int index, SharedSessionContractImplementor session) throws SQLException {
        System.out.println("");
    }

    @Override
    public ArrayList deepCopy(ArrayList value) {
        List list = new ArrayList();
        for (Object object : value) {
            list.add(object);
        }
        return (ArrayList) list;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(ArrayList value) {
        return SerializationUtils.serialize(value);
    }

    @Override
    public ArrayList assemble(Serializable cached, Object owner) {
        return SerializationUtils.deserialize((byte[]) cached);
    }

    @Override
    public ArrayList replace(ArrayList detached, ArrayList managed, Object owner) {
        return null;
    }
}
