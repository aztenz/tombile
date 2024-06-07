package com.n2o.tombile.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ObjectCloner {

    public static void cloneProperties(Object source, Object target) throws IllegalAccessException {
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();

        Map<String, Field> sourceFieldMap = getFieldMap(sourceClass);
        Map<String, Field> targetFieldMap = getFieldMap(targetClass);

        for (Map.Entry<String, Field> entry : sourceFieldMap.entrySet()) {
            String fieldName = entry.getKey();
            Field sourceField = entry.getValue();
            Field targetField = targetFieldMap.get(fieldName);

            if (targetField != null) {
                copyField(source, target, sourceField, targetField);
            }
        }
    }

    private static Map<String, Field> getFieldMap(Class<?> clazz) {
        Map<String, Field> fieldMap = new HashMap<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                fieldMap.put(field.getName(), field);
            }
            clazz = clazz.getSuperclass();
        }
        return fieldMap;
    }

    @SuppressWarnings("unchecked")
    private static void copyField(Object source, Object target, Field sourceField, Field targetField) throws IllegalAccessException {
        Object value = sourceField.get(source);
        Class<?> sourceFieldType = sourceField.getType();
        Class<?> targetFieldType = targetField.getType();

        if (sourceFieldType.isEnum() && targetFieldType.equals(String.class)) {
            targetField.set(target, ((Enum<?>) value).name());
        } else if (sourceFieldType.equals(String.class) && targetFieldType.isEnum()) {
            targetField.set(target, Enum.valueOf((Class<Enum>) targetFieldType, ((String) value).toUpperCase()));
        } else if (targetFieldType.isAssignableFrom(sourceFieldType)) {
            targetField.set(target, value);
        }
    }
}
