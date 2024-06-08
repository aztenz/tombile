package com.n2o.tombile.auth.service;

import com.n2o.tombile.auth.model.enums.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RoleStrategyFactory {
    private final Map<Role, RoleStrategy> strategies;

    @PostConstruct
    public void init() {
        strategies.put(Role.USER, new RoleStrategyUser());
        strategies.put(Role.SUPPLIER, new RoleStrategySupplier());
    }

    public RoleStrategy getStrategy(Role role) {
        return strategies.get(role);
    }
}
