package org.whilmarbitoco.app.util;

import org.whilmarbitoco.app.anotation.Column;
import org.whilmarbitoco.app.anotation.Primary;
import org.whilmarbitoco.app.anotation.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Entity<T> {

    private Class<T> type;

    public Entity(Class<T> type) {
        this.type = type;
    }


    public final List<String> getColumns() {
        return Arrays.stream(type.getDeclaredFields())
                .filter(field -> field.getAnnotation(Primary.class) == null)
                .map(Field::getName).toList();
    }

    public final String getTable() {
        return type.getAnnotation(Table.class).name();
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

}
