package com.n2o.tombile.service.product;

import com.n2o.tombile.dto.response.product.car.*;
import com.n2o.tombile.enums.ProductType;
import com.n2o.tombile.model.Car;
import com.n2o.tombile.repository.product.CarRepository;
import org.springframework.stereotype.Service;

@Service
public class CarService extends ProductService<Car, CarRepository> {

    public CarService(CarRepository carRepository) {
        super(carRepository);
    }

    @Override
    public Class<Car> getProductClass() {
        return Car.class;
    }

    @Override
    public Class<PersistCarRSP> getPersistProductRSPClass() {
        return PersistCarRSP.class;
    }

    @Override
    public Class<CarListItem> getProductListItemClass() {
        return CarListItem.class;
    }

    @Override
    public Class<CarDetails> getProductDetailsClass() {
        return CarDetails.class;
    }


    @Override
    public void setProductSpecificDetails(Car product) {
        product.setName(product.getMake() + " " + product.getModel() + " " + product.getYear());
        product.setProductType(ProductType.CAR);
    }

}
