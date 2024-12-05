package me.fairygel.fbook.util;

import lombok.SneakyThrows;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;

public class PropertyMerger {

    @SneakyThrows
    public static void merge(Object source, Object destination) {
        if (source == null || destination == null) return;

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