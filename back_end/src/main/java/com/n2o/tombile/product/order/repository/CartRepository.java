package com.n2o.tombile.product.order.repository;

import com.n2o.tombile.product.order.model.cart.Cart;
import com.n2o.tombile.product.order.model.cart.CartId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, CartId> {
}
