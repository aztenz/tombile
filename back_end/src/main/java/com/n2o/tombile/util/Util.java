package com.n2o.tombile.util;

import com.n2o.tombile.exception.ItemNotFoundException;
import com.n2o.tombile.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class Util {
    private static final String ERROR_COPYING_PROPERTIES = "Error copying properties: ";
    private static final String ERROR_GETTING_ITEM_BY_ID = "Error getting item by id ";
    private static final String ERROR_GETTING_ITEMS = "Error getting items ";
    private static final String ERROR_SAVING_ITEM = "Error saving item ";
    private static final String ERROR_DELETING_ITEM = "Error deleting item ";
    private static final String ITEM_NOT_FOUND = "Item not found";
    public static final String ERROR_VALIDATING_ITEM_EXISTENCE = "Error Validating Item existence ";

    public static int getCurrentUserId() {
        return ((User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getId();
    }

    public static <T> T cloneObject(
            Object source,
            Class<T> targetType
    ) {
        T targetObject;
        try {
            targetObject = targetType.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, targetObject);
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
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            throw new RuntimeException(ERROR_COPYING_PROPERTIES + e.getMessage(), e);
        }
    }

    public static  <DTOClass, Id, EntityClass> DTOClass saveItem(
            Class<DTOClass> aClass,
            EntityClass item,
            JpaRepository<EntityClass, Id> repository
    ) {
        try {
            repository.save(item);
            return cloneObject(item, aClass);
        } catch (Exception e) {
            throw new RuntimeException(ERROR_SAVING_ITEM + e.getMessage(), e);
        }
    }

    public static  <DTOClass, Id, EntityClass> DTOClass getItemById(
            Class<DTOClass> dtoClass,
            Id id,
            JpaRepository<EntityClass, Id> repository
    ) {
        try {
            EntityClass item = repository.findById(id)
                    .orElseThrow(() -> new ItemNotFoundException(ITEM_NOT_FOUND));
            return cloneObject(item, dtoClass);
        } catch (ItemNotFoundException e) {
            throw new ItemNotFoundException(ITEM_NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(ERROR_GETTING_ITEM_BY_ID + e.getMessage(), e);
        }
    }

    public static  <DTOClass, Id, EntityClass> List<DTOClass> getAllItems(
            Class<DTOClass> dtoClass,
            JpaRepository<EntityClass, Id> repository
    ) {
        try {
            List<EntityClass> entityItems = repository.findAll();
            List<DTOClass> dtoItems = new ArrayList<>();
            entityItems.forEach(entity -> dtoItems.add(cloneObject(entity, dtoClass)));
            return dtoItems;
        } catch (Exception e) {
            throw new RuntimeException(ERROR_GETTING_ITEMS + e.getMessage(), e);
        }
    }

    public static <Id, EntityClass> void deleteItem(
            Id id,
            JpaRepository<EntityClass, Id> repository
    ) {
        try {
            EntityClass item = repository.findById(id)
                    .orElseThrow(() -> new ItemNotFoundException(ITEM_NOT_FOUND));
            repository.delete(item);
        } catch (ItemNotFoundException e) {
            throw new ItemNotFoundException(ITEM_NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(ERROR_DELETING_ITEM + e.getMessage(), e);
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
