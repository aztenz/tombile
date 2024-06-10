package com.n2o.tombile.core.user.service;

import com.n2o.tombile.core.user.model.User;

public interface RoleStrategy {
    String handleAfterVerifyEmail(User user);
}
