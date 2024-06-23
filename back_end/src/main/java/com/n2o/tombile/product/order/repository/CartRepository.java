package com.n2o.tombile.product.order.repository;

import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.product.order.model.cart.Cart;
import com.n2o.tombile.product.order.model.cart.CartId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, CartId> {
    void deleteAllByUserId(int userId);
    List<Cart> findAllByUser(User user);
}
