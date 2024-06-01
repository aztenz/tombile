package com.n2o.tombile.util;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


public class ObjectCloner {
    @SuppressWarnings("unchecked")
    public static void cloneProperties(Object source, Object target) throws IllegalAccessException {
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();

        Field[] sourceFields = getAllFields(sourceClass);
        Field[] targetFields = getAllFields(targetClass);

        Map<String, Field> targetFieldMap = new HashMap<>();
        for (Field targetField : targetFields) {
            targetFieldMap.put(targetField.getName(), targetField);
        }

        for (Field sourceField : sourceFields) {
            Field targetField = targetFieldMap.get(sourceField.getName());
            if (targetField != null) {
                sourceField.setAccessible(true);
                targetField.setAccessible(true);
                Object value = sourceField.get(source);
                Class<?> sourceFieldType = sourceField.getType();
                Class<?> targetFieldType = targetField.getType();

                if (sourceFieldType.isEnum() && targetFieldType.equals(String.class)) {
                    // Convert enum to string
                    targetField.set(target, ((Enum<?>) value).name());
                } else if (sourceFieldType.equals(String.class) && targetFieldType.isEnum()) {
                    // Convert string to enum
                    targetField.set(target, Enum.valueOf((Class<Enum>) targetFieldType, (String) value));
                } else if (targetFieldType.isAssignableFrom(sourceFieldType)) {
                    targetField.set(target, value);
                }
            }
        }
    }

    private static Field[] getAllFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            Field[] superFields = getAllFields(superClass);
            Field[] combinedFields = new Field[fields.length + superFields.length];
            System.arraycopy(fields, 0, combinedFields, 0, fields.length);
            System.arraycopy(superFields, 0, combinedFields, fields.length, superFields.length);
            fields = combinedFields;
        }
        return fields;
    }
}