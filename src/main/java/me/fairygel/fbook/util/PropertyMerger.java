package me.fairygel.fbook.util;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class PropertyMerger {

    @SneakyThrows
    public static void merge(Object source, Object destination) {
        if (source == null || destination == null) return;

        Field[] fields = source.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            Object value = field.get(source);

            if (value != null) {
                field.set(destination, value);
            }
        }
    }
    private PropertyMerger(){}
}