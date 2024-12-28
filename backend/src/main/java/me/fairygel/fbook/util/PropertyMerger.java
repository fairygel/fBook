package me.fairygel.fbook.util;

import lombok.SneakyThrows;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;

public class PropertyMerger {

    /*
     * Property merger class works like this:
     * we have source, with fields: age = 11, name = null
     * also we have destination, with fields age = 13, name = "John"
     * we will copy all non-null fields from source to destination, so
     * we will go to first field - age.
     * age in source is not null, so, we will copy it to destination.
     * next is name. source is null, so, we will not copy this field to destination.
     * our result is object, with fields: age = 13, name = "John".
     * also, if field is class, that implements Serializable interface,
     * we will merge all properties from that field to another
     */
    // TODO: use getters and setters instead of field access
    @SneakyThrows
    public static void merge(Object source, Object destination) {
        if (source == null || destination == null) return;
        if (source.getClass() != destination.getClass()) return;

        Field[] fields = source.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (Arrays.asList(field.getClass().getInterfaces()).contains(Serializable.class)) {
                merge(field, destination.getClass().getField(field.getName()));
            }

            field.setAccessible(true);

            Object value = field.get(source);

            if (value != null) {
                field.set(destination, value);
            }
        }
    }
    private PropertyMerger(){}
}