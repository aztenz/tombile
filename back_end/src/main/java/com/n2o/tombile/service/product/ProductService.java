package com.n2o.tombile.service.product;

import com.n2o.tombile.dto.response.product.abs_product.*;
import com.n2o.tombile.core.common.exception.ItemNotFoundException;
import com.n2o.tombile.model.Product;
import com.n2o.tombile.repository.product.ProductRepository;
import com.n2o.tombile.service.CRUDService;
import com.n2o.tombile.core.common.util.Util;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public abstract class ProductService<P extends Product, R extends ProductRepository<P>>
        implements CRUDService<P, Integer, R> {

    private static final String PRODUCT_NOT_FOUND = "Cannot find the product for the given user";
    private final R productRepository;

    public abstract Class<P> getProductClass();
    public abstract Class<? extends PersistProductRSP> getPersistProductRSPClass();
    public abstract Class<? extends ProductListItem> getProductListItemClass();
    public abstract Class<? extends ProductDetails> getProductDetailsClass();
    public abstract void setProductSpecificDetails(P product);

    @Override
    public Object addItem(Object request) {
        try {
            P product = Util.cloneObject(request, getProductClass());
            product.setSupplier(Util.getCurrentUser());
            setProductSpecificDetails(product);
            productRepository.save(product);
            return getPersistProductRSP(product);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Object editItem(
            Object request,
            Integer id
    ) {
        try {
            P product = productRepository.findUserProductById(id, Util.getCurrentUserId())
                    .orElseThrow(() -> new ItemNotFoundException(PRODUCT_NOT_FOUND));
            Util.copyProperties(request, product);
            product = productRepository.save(product);
            return getPersistProductRSP(product);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Object> getAllUserItems() {
        try {
            List<P> products = productRepository
                    .findAllUserProducts(Util.getCurrentUserId());
            List<Object> listItems = new ArrayList<>();
            products.forEach(product -> listItems.add(Util.cloneObject(product, getProductListItemClass())));
            return listItems;
        } catch (Exception e) {
            throw e;
        }
    }

    public Object getUserItemById(int id) {
        try {
            P product = productRepository
                    .findUserProductById(id, Util.getCurrentUserId())
                    .orElseThrow(() -> new ItemNotFoundException(PRODUCT_NOT_FOUND));
            return Util.cloneObject(product, getProductDetailsClass());
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteUserItemById(int id) {
        try {
            P product = productRepository
                    .findUserProductById(id, Util.getCurrentUserId())
                    .orElseThrow(() -> new ItemNotFoundException(PRODUCT_NOT_FOUND));
            productRepository.delete(product);
        } catch (Exception e) {
            throw e;
        }
    }

    private @NotNull PersistProductRSP getPersistProductRSP(P product) {
        PersistProductRSP postProductRSP = Util.cloneObject(product, getPersistProductRSPClass());
        postProductRSP.setSupplierName("");
        postProductRSP.setSupplierEmail(Util.getCurrentUser().getUserData().getEmail());
        return postProductRSP;
    }

    @Override
    public R getRepository() {
        return productRepository;
    }

    @Override
    public Class<P> getEntity() {
        return getProductClass();
    }

    @Override
    public Class<?> getPersistRSP() {
        return getPersistProductRSPClass();
    }

    @Override
    public Class<?> getListItem() {
        return getProductListItemClass();
    }

    @Override
    public Class<?> getDetails() {
        return getProductDetailsClass();
    }
}
