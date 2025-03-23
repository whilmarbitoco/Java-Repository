package org.whilmarbitoco.app.util;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Mapper<T> {

    private Class<T> type;

    public Mapper(Class<T> type) {
        this.type = type;
    }

    public T to(ResultSet rs) {
        try {
            T obj = type.getDeclaredConstructor().newInstance();

            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = rs.getObject(field.getName());
                field.set(obj, value);
            }

            return obj;
        } catch (Exception e) {
            throw new RuntimeException("[Mapper] " + e);
        }
    }

    public PreparedStatement from(T entity, PreparedStatement stmt) {
        try {
            Field[] fields = entity.getClass().getDeclaredFields();

            for (int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                stmt.setObject(i, fields[i].get(entity));
            }

            return stmt;
        } catch (Exception e) {
            throw new RuntimeException("[Mapper] " + e);
        }
    }

}
