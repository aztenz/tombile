package com.n2o.tombile.product.product.service;

import com.n2o.tombile.core.common.exception.ItemNotFoundException;
import com.n2o.tombile.core.common.service.CRUDService;
import com.n2o.tombile.core.common.util.Util;
import com.n2o.tombile.core.user.service.RoleStrategyFactory;
import com.n2o.tombile.product.product.dto.RSPPersistProduct;
import com.n2o.tombile.product.product.dto.RSPProductDetails;
import com.n2o.tombile.product.product.dto.RSPProductListItem;
import com.n2o.tombile.product.product.model.Product;
import com.n2o.tombile.product.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.n2o.tombile.core.common.util.Constants.ERROR_PRODUCT_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public abstract class ProductService<P extends Product, R extends ProductRepository<P>>
        implements CRUDService<P, Integer, R> {

    private final R productRepository;
    private final RoleStrategyFactory roleStrategyFactory;

    public abstract Class<P> getProductClass();
    public abstract Class<? extends RSPPersistProduct> getPersistProductRSPClass();
    public abstract Class<? extends RSPProductListItem> getProductListItemClass();
    public abstract Class<? extends RSPProductDetails> getProductDetailsClass();
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
    public List<Object> getAll() {
        List<P> products = roleStrategyFactory
                .getStrategy(Util.getCurrentUser().getUserData().getRole())
                .getProducts(productRepository);
        List<Object> productList = new ArrayList<>();
        products.forEach(product -> productList.add(Util.cloneObject(product, getProductListItemClass())));
        return productList;
    }

    @Override
    public void deleteItem(Integer id) {
        productRepository.delete(roleStrategyFactory
                .getStrategy(Util.getCurrentUser().getUserData().getRole())
                .getProductById(productRepository, id));
    }

    @Override
    public Object getItemById(Integer id) {
        P product = roleStrategyFactory
                .getStrategy(Util.getCurrentUser().getUserData().getRole())
                .getProductById(productRepository, id);
        RSPProductDetails productDetails = Util.cloneObject(product, getProductDetailsClass());
        productDetails.setSupplierName(getSupplierName(product));
        productDetails.setSupplierEmail(product.getSupplier().getUserData().getEmail());
        return productDetails;
    }

    private static <P extends Product> @NotNull String getSupplierName(P product) {
        return product.getSupplier().getUserData().getFirstName()
                + " " +
                product.getSupplier().getUserData().getLastName();
    }

    @Override
    public Object editItem(
            Object request,
            Integer id
    ) {
        try {
            P product = productRepository
                    .findUserProductById(id, Util.getCurrentUserId(), productRepository.getProductType())
                    .orElseThrow(() -> new ItemNotFoundException(ERROR_PRODUCT_NOT_FOUND));
            Util.copyProperties(request, product);
            product = productRepository.save(product);
            return getPersistProductRSP(product);
        } catch (Exception e) {
            throw e;
        }
    }

    public P getProductEntityById(int id) {
        return productRepository
                .findById(id).orElseThrow(() -> new ItemNotFoundException(ERROR_PRODUCT_NOT_FOUND));
    }

    private RSPPersistProduct getPersistProductRSP(P product) {
        RSPPersistProduct postProductRSP = Util.cloneObject(product, getPersistProductRSPClass());
        postProductRSP.setSupplierName(getSupplierName());
        postProductRSP.setSupplierEmail(Util.getCurrentUser().getUserData().getEmail());
        return postProductRSP;
    }

    private String getSupplierName() {
        String firstName = Util.getCurrentUser().getUserData().getFirstName();
        String lastName = Util.getCurrentUser().getUserData().getLastName();
        return  firstName + " " + lastName;
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
