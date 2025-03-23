package org.whilmarbitoco.app.util;

import org.whilmarbitoco.app.anotation.Column;
import org.whilmarbitoco.app.anotation.Primary;
import org.whilmarbitoco.app.anotation.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Entity<T> {

    private Class<T> type;

    public Entity(Class<T> type) {
        this.type = type;
    }


    public final List<String> getColumns() {
        return Arrays.stream(type.getDeclaredFields())
                .filter(field -> field.getAnnotation(Primary.class) == null)
                .map(field -> field.getAnnotation(Column.class).name()).toList();
    }

    public final String getTable() {
        return type.getAnnotation(Table.class).name();
    }

    public Optional<String> getPrimaryKey() {
        return Arrays.stream(type.getDeclaredFields())
                .filter(field -> field.getAnnotation(Primary.class) != null)
                .map(Field::getName).findFirst();
    }

    public Object getPrimaryKeyValue(T entity) {
        try {
            Field[] fields = entity.getClass().getDeclaredFields();
            for (Field field : fields) {
                Primary primary = field.getAnnotation(Primary.class);
                if (primary == null) continue;
                field.setAccessible(true);
                return field.get(entity);
            }
            return null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void validate(T entity) {
        try {
            Field[] fields = entity.getClass().getDeclaredFields();

            for (Field field : fields) {
                Column column = field.getAnnotation(Column.class);
                if (column == null) continue;
                field.setAccessible(true);
                if (field.get(entity) == null)
                    throw new RuntimeException("Field cannot be empty for " + column.name());
            }
        } catch (Exception err) {
            throw new RuntimeException("[Entity.java] Something went wrong. INFO:: " + err.getMessage());
        }
    }

    public void validate(String column) {
        try {
            Field[] fields = type.getDeclaredFields();

            for (Field field : fields) {
                Column col = field.getAnnotation(Column.class);
                if (col == null) continue;
                if (col.name().equals(column)) return;
            }
            throw new RuntimeException("Unknown column " + column);
        } catch (Exception err) {
            throw new RuntimeException("[Entity.java] Something went wrong. INFO:: " + err.getMessage());
        }
    }
}
