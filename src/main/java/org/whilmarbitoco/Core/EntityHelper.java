package org.whilmarbitoco.Core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityHelper<T> {

    private final Class<T> entity;

    public EntityHelper(Class<T> entity) {
        this.entity = entity;
    }

    public boolean validateAllFields(T entt) {
      try {
          for (Field field : entt.getClass().getDeclaredFields()) {
              field.setAccessible(true);
              if (field.get(entity) == null) return false;
          }
      } catch (Exception e) {
          throw new RuntimeException();
      }
      return true;
    }

    public boolean validateField(String field) {
        try {
            for (Field fld : entity.getDeclaredFields()) {
                fld.setAccessible(true);
                if (fld.get(entity).equals(field)) return true;
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return false;
    }

    public String getFields() {
        return Arrays.stream(entity.getDeclaredFields())
                .map(Field::getName)
                .filter(name -> !name.equals("id"))
                .collect(Collectors.joining(", "));
    }

    public String getFieldForUpdate() {
        return Arrays.stream(entity.getDeclaredFields())
                .map(Field::getName)
                .filter(name -> !name.equals("id"))
                .collect(Collectors.joining("= ?, "));
    }

}
