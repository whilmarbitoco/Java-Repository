package org.whilmarbitoco.Core;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Mapper<T> {

    private final Class<T> entity;

    public Mapper(Class<T> entity) {
        this.entity = entity;
    }

    public T to(ResultSet rs)  {
        try {
            T obj = entity.getDeclaredConstructor().newInstance();

            for (Field field : entity.getDeclaredFields()) {
                field.setAccessible(true);

                Object value = rs.getObject(field.getName());
                field.set(obj, value);
            }

            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }
}
