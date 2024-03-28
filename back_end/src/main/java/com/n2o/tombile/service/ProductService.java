package com.n2o.tombile.service;

import com.n2o.tombile.enums.ProductType;
import com.n2o.tombile.model.Product;
import com.n2o.tombile.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static com.n2o.tombile.util.Util.cloneObject;
import static com.n2o.tombile.util.Util.copyProperties;
import static com.n2o.tombile.util.Util.deleteItem;
import static com.n2o.tombile.util.Util.getAllItems;
import static com.n2o.tombile.util.Util.getCurrentUserId;
import static com.n2o.tombile.util.Util.getItemById;
import static com.n2o.tombile.util.Util.saveItem;

@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    public <DTOResponse, DTORequest, EntityClass> DTOResponse saveProduct(
            Class<DTOResponse> aClass,
            Class<EntityClass> productClass,
            String name,
            ProductType productType,
            DTORequest request
    ) {
        Product product = createProductModel(productClass, request, name, productType);
        return saveItem(aClass, product, productRepository);
    }

    public <DTOResponse, DTORequest, EntityClass> DTOResponse editProduct(
            Class<DTOResponse> responseClass,
            Class<EntityClass> productClass,
            DTORequest request,
            int id,
            JpaRepository<EntityClass, Integer> repository
    ) {
        Product product = (Product) getProductById(productClass, id, repository);
        copyProperties(request, product);
        return saveItem(responseClass, product, productRepository);
    }

    public <DTOClass, EntityClass> DTOClass getProductById(
            Class<DTOClass> dtoClass,
            int id,
            JpaRepository<EntityClass, Integer> repository
    ) {
        return getItemById(dtoClass, id, repository);
    }

    public <DTOClass, EntityClass> List<DTOClass> getAllProducts(
            Class<DTOClass> dtoClass,
            JpaRepository<EntityClass, Integer> repository
    ) {
        return getAllItems(dtoClass, repository);
    }

    public <EntityClass, Id> void deleteProduct(
            Id id,
            JpaRepository<EntityClass, Id> repository
    ) {
        deleteItem(id, repository);
    }

    private <DTOClass, EntityClass> Product createProductModel(
            Class<EntityClass> entity,
            DTOClass dto,
            String name,
            ProductType productType
    ) {
        Product product = (Product) cloneObject(dto, entity);
        product.setSupplierId(getCurrentUserId());
        product.setName(name);
        product.setProductType(productType);
        return product;
    }
}
