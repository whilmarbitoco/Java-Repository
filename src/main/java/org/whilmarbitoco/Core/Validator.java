package org.whilmarbitoco.Core;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Validator<T> {

    private Class<T> entity;

    public Validator(Class<T> entity) {
        this.entity = entity;
    }

    public boolean entitiy(T entity)  {
        Field[] fields = entity.getClass().getDeclaredFields();

      try {
          for (int i = 1; i < fields.length; i++) {
              fields[i].setAccessible(true);

              if (fields[i].get(entity) == null) {
                  return false;
              }
          }
      } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
      }

        return true;
    }

    public String field(String field) {
        return Arrays.stream(entity.getDeclaredFields())
                .map(Field::getName)
                .filter(fld -> fld.equals(field))
                .findFirst().orElse(null);

    }


}
