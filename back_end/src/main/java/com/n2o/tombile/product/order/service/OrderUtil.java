package com.n2o.tombile.product.order.service;

import com.n2o.tombile.core.common.exception.SQLValidationException;

import java.sql.SQLException;

class OrderUtil {
    static void handleException(Exception e) {
        Throwable cause = e.getCause();
        if(cause == null) return;
        cause = cause.getCause();
        if (cause instanceof SQLException sqlException
                && "45000".equals(sqlException.getSQLState())) {
            throw new SQLValidationException(sqlException.getMessage());
        }
    }
}
