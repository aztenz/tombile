package com.n2o.tombile.util;

import com.n2o.tombile.exception.ItemNotFoundException;
import com.n2o.tombile.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class Util {
    private static final String ERROR_COPYING_PROPERTIES = "Error copying properties: ";
    private static final String ITEM_NOT_FOUND = "Item not found";
    private static final String ERROR_VALIDATING_ITEM_EXISTENCE = "Error Validating Item existence ";

    public static int getCurrentUserId() {
        return ((User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getId();
    }

    public static User getCurrentUser() {
        return ((User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
    }

    public static <T> T cloneObject(
            Object source,
            Class<T> targetType
    ) {
        T targetObject;
        try {
            targetObject = targetType.getDeclaredConstructor().newInstance();
            ObjectCloner.cloneProperties(source, targetObject);
        } catch (Exception e) {
            throw new RuntimeException(ERROR_COPYING_PROPERTIES + e.getMessage(), e);
        }
        return targetObject;
    }

    public static<T, R> void copyProperties(
            T source,
            R target
    ) {
        try {
            ObjectCloner.cloneProperties(source, target);
        } catch (Exception e) {
            throw new RuntimeException(ERROR_COPYING_PROPERTIES + e.getMessage(), e);
        }
    }

    public static <Id, EntityClass> void validateItemExistence(
            Id id,
            JpaRepository<EntityClass, Id> repository
    ) {
        try {
            if(!repository.existsById(id))
                throw new ItemNotFoundException(ITEM_NOT_FOUND);
        } catch (ItemNotFoundException e) {
            throw new ItemNotFoundException(ITEM_NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(ERROR_VALIDATING_ITEM_EXISTENCE + e.getMessage(), e);
        }
    }
}
