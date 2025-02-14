package org.whilmarbitoco.Core;

import java.lang.reflect.Field;
import java.sql.ResultSet;

public class Mapper<T> {

    private Class<T> entity;

    public Mapper(Class<T> entity) {
        this.entity = entity;
    }

    public T map(ResultSet rs)  {

        try {
            T obj = entity.getDeclaredConstructor().newInstance();

            for (Field field : entity.getDeclaredFields()) {
                field.setAccessible(true);

                Object value = rs.getObject(field.getName());
                field.set(obj, value);
            }

            return obj;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
