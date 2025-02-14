package org.whilmarbitoco.Core;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntityMapper<T> {
    private final Class<T> entityType;

    public EntityMapper(Class<T> entityType) {
        this.entityType = entityType;
    }

    public List<T> map(ResultSet rs) {
        List<T> results = new ArrayList<>();

        try {
            while (rs.next()) {
                T obj = this.entityType.getDeclaredConstructor().newInstance();

                for (Field field : entityType.getDeclaredFields()) {
                    field.setAccessible(true);

                    Object value = rs.getObject(field.getName());
                    field.set(obj, value);
                }

                results.add(obj);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    public PreparedStatement map(T entity, PreparedStatement stmt) {
        try {
            Field[] fields = entity.getClass().getDeclaredFields();

            for (int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                stmt.setObject(i, fields[i].get(entity));
            }

            return stmt;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
