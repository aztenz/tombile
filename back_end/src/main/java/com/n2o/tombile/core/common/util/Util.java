package com.n2o.tombile.core.common.util;

import com.n2o.tombile.core.user.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.n2o.tombile.core.common.util.Constants.ERROR_COPYING_PROPERTIES;

public abstract class Util {

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
            throw new RuntimeException(ERROR_COPYING_PROPERTIES, e);
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
            throw new RuntimeException(ERROR_COPYING_PROPERTIES, e);
        }
    }
}
