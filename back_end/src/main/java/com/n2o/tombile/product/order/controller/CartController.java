package com.n2o.tombile.product.order.controller;

import com.n2o.tombile.product.order.dto.RQCart;
import com.n2o.tombile.product.order.dto.RSPCart;
import com.n2o.tombile.product.order.dto.RSPPersistCart;
import com.n2o.tombile.product.order.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<RSPPersistCart> addToCart(@RequestBody RQCart request) {
        RSPPersistCart response = cartService.addToCart(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/{pId}")
    public void removeFromCart(@PathVariable int pId) {
        cartService.removeFromCart(pId);
    }

    @GetMapping
    public ResponseEntity<List<RSPCart>> getAllCarts() {
        List<RSPCart> responses = cartService.getCarts();
        return responses.isEmpty()? ResponseEntity.noContent().build() : ResponseEntity.ok(responses);
    }

    @GetMapping("/{pId}")
    public ResponseEntity<RSPCart> getCartByProductId(@PathVariable int pId) {
        return ResponseEntity.ok(cartService.getCartById(pId));
    }

}
