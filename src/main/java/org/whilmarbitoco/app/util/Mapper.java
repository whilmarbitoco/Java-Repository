package org.whilmarbitoco.app.util;

import org.whilmarbitoco.app.anotation.Column;

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
                Column column = field.getAnnotation(Column.class);
                if (column == null) continue;

                field.setAccessible(true);
                Object value = rs.getObject(column.name());
                field.set(obj, value);
            }

            return obj;
        } catch (Exception e) {
            throw new RuntimeException("[Mapper] " + e);
        }
    }


    /**
     * ⚠ Used with caution: This method assumes that all fields are perfectly aligned.
     * If the column order is mismatched, incorrect data insertion may occur.
     * <p>
     * Ensure that column mappings are properly configured to prevent potential vulnerabilities.
     * </p>
     * -- The Developer (wb2c0)
     */
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
