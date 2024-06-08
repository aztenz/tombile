package com.n2o.tombile.auth.service;

import com.n2o.tombile.auth.model.entity.User;

public interface RoleStrategy {
    String handleAfterVerifyEmail(User user);
}
