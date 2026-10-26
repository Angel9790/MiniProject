
package model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.annotation.Id;

public class Repository<T> {

    private final HashMap<String, T> items = new HashMap<>();

    public T save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException(
                "Entity cannot be null."
            );
        }

        try {
            Field idField = null;
            Class<?> current = entity.getClass();

            while (current != null && idField == null) {
                for (Field field : current.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Id.class)) {
                        idField = field;
                        break;
                    }
                }

                current = current.getSuperclass();
            }

            if (idField == null) {
                throw new IllegalArgumentException(
                    "No field marked with @Id was found."
                );
            }

            idField.setAccessible(true);
            Object idValue = idField.get(entity);

            if (idValue == null || idValue.toString().isBlank()) {
                throw new IllegalArgumentException(
                    "The @Id field cannot be empty."
                );
            }

            items.put(idValue.toString(), entity);
            return entity;

        } catch (IllegalAccessException e) {
            throw new IllegalStateException(
                "Unable to read the @Id field.", e
            );
        }
    }

    @SafeVarargs
    public final void saveAll(T... entities) {
        for (T entity : entities) {
            save(entity);
        }
    }

    public T findById(String id) {
        return items.get(id);
    }

    public ArrayList<T> findAll() {
        return new ArrayList<>(items.values());
    }

    public boolean delete(String id) {
        return items.remove(id) != null;
    }

    public int size() {
        return items.size();
    }
}
