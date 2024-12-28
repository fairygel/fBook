package me.fairygel.fbook.util;

import com.sun.jdi.ClassNotPreparedException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

// TODO: make method, that will transform class to enum, using data from repository
@Component
public class ClassEnumerator {
    public <T, U extends CrudRepository<T, Short>> void initializeFromRepository(
            U repository, T entity) {

        if (!haveNameAndIdFields(entity)) return;

        Map<String, T> entities = getEntitiesFromRepository(repository);
    }

    private <T, U extends CrudRepository<T, Short>> Map<String, T> getEntitiesFromRepository(U repository) {
        List<T> entities = new ArrayList<>();
        repository.findAll().forEach(entities::add);

        // sorting, so we will know, that all entities are in correct order
        entities.sort(Comparator.comparing(e -> {
            try {
                return e.getClass().getDeclaredField("id").getShort(e);
            } catch (IllegalAccessException | NoSuchFieldException ex) {
                throw new IllegalCallerException(ex.getMessage());
            }
        }));

        Map<String, T> map = new LinkedHashMap<>();
        
        for (T entity : entities) {
            String entityName;
            
            try {
                entityName = (String) entity.getClass().getDeclaredMethod("getName").invoke(entity);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new IllegalCallerException(e.getMessage());
            }
            
            map.put(entityName.toUpperCase().replace(" ", "_"), entity);
        }
        
        return map;
    }

    private <T> boolean haveNameAndIdFields(T entity) {
        Set<Field> fields = Set.of(entity.getClass().getDeclaredFields());

        boolean containsIdField = false;
        boolean containsNameField = false;

        for (Field field : fields) {
            if (field.getName().equals("name") && field.getType().equals(String.class)) {
                containsNameField = true;
            }

            if (field.getName().equals("id") && field.getType().equals(Short.class)) {
                containsIdField = true;
            }

            if (containsIdField && containsNameField) break;
        }
        return containsIdField && containsNameField;
    }

    public ClassEnumerator() {
        throw new ClassNotPreparedException("class is not implemented yet");
    }
}
