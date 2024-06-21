package com.n2o.tombile.product.order.service;

import com.n2o.tombile.core.common.exception.ItemNotFoundException;
import com.n2o.tombile.core.common.util.Util;
import com.n2o.tombile.product.order.dto.RQCart;
import com.n2o.tombile.product.order.dto.RSPCart;
import com.n2o.tombile.product.order.dto.RSPPersistCart;
import com.n2o.tombile.product.order.model.cart.Cart;
import com.n2o.tombile.product.order.model.cart.CartId;
import com.n2o.tombile.product.order.repository.CartRepository;
import com.n2o.tombile.product.part.service.CarPartService;
import com.n2o.tombile.product.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.n2o.tombile.core.common.util.Constants.CART_SUCCESSFULLY_ADDED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_CART_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CarPartService carPartService;
    private final CartRepository cartRepository;

    public RSPPersistCart updateCart(RQCart request) {
        try {
            Product product = getProduct(request.getProductId());

            CartId cartId = createCartId(request.getProductId());

            persistToCart(request, cartId, product);

            return createResponse(cartId.getProductId());
        } catch (Exception e) {
            OrderUtil.handleException(e);
            throw e;
        }
    }

    public void removeFromCart(int pId) {
        CartId cartId = createCartId(pId);
        cartRepository.deleteById(cartId);
    }

    public List<RSPCart> getCarts() {
        List<Cart> carts = getAllCarts();
        List<RSPCart> responses = new ArrayList<>();
        carts.forEach(cart -> responses.add(prepareCartDto(cart)));
        return responses;
    }

    public RSPCart getCartById(int pId) {
        Cart cart = getCartByProductId(pId);
        return prepareCartDto(cart);
    }

    private void persistToCart(RQCart request, CartId cartId, Product product) {
        cartRepository.findById(cartId).ifPresentOrElse(cart -> {
            cart.setQuantity(request.getQuantity());
            cartRepository.save(cart);
        }, () -> {
            Cart cart = new Cart();
            cart.setId(cartId);
            cart.setQuantity(request.getQuantity());
            cart.setUser(Util.getCurrentUser());
            cart.setProduct(product);
            cartRepository.save(cart);
        });
    }

    private Product getProduct(int productId) {
        return carPartService.getProductEntityById(productId);
    }

    private List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    private Cart getCartByProductId(int pId) {
        CartId cartId = createCartId(pId);
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ItemNotFoundException(ERROR_CART_NOT_FOUND));
    }

    private CartId createCartId(int productId) {
        CartId cartId = new CartId();
        cartId.setProductId(productId);
        cartId.setUserId(Util.getCurrentUserId());
        return cartId;
    }

    private RSPPersistCart createResponse(int id) {
        RSPPersistCart response = new RSPPersistCart();
        response.setId(id);
        response.setMessage(CART_SUCCESSFULLY_ADDED);
        return response;
    }

    private RSPCart prepareCartDto(Cart cart) {
        RSPCart rspCart = new RSPCart();
        rspCart.setProductName(cart.getProduct().getName());
        rspCart.setProductDescription(cart.getProduct().getDescription());
        rspCart.setProductPrice(cart.getProduct().getPrice());
        rspCart.setSupplierName(getSupplierName(cart));
        rspCart.setSupplierEmail(cart.getProduct().getSupplier().getUserData().getEmail());
        rspCart.setQuantity(cart.getQuantity());
        rspCart.setSubtotal(cart.getSubtotal());
        return rspCart;
    }

    private String getSupplierName(Cart cart) {
        return cart.getProduct().getSupplier().getUserData().getFirstName() +
                " "
                + cart.getProduct().getSupplier().getUserData().getLastName();
    }
}
