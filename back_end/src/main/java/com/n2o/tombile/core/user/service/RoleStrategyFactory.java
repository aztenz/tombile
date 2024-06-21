package com.n2o.tombile.core.user.service;

import com.n2o.tombile.core.user.model.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RoleStrategyFactory {
    private final Map<Role, RoleStrategy> strategies = new HashMap<>();

    @PostConstruct
    public void init() {
        strategies.put(Role.USER, new RoleStrategyUser());
        strategies.put(Role.SUPPLIER, new RoleStrategySupplier());
    }

    public RoleStrategy getStrategy(Role role) {
        return strategies.get(role);
    }
}
