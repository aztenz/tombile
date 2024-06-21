package com.n2o.tombile.product.car.service;

import com.n2o.tombile.product.product.model.ProductType;
import com.n2o.tombile.product.car.model.Car;
import com.n2o.tombile.product.car.dto.RSPCarDetails;
import com.n2o.tombile.product.car.dto.RSPCarListItem;
import com.n2o.tombile.product.car.dto.RSPPersistCar;
import com.n2o.tombile.product.product.service.ProductService;
import com.n2o.tombile.product.car.repository.CarRepository;
import org.jetbrains.annotations.NotNull;
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
    public Class<RSPPersistCar> getPersistProductRSPClass() {
        return RSPPersistCar.class;
    }

    @Override
    public Class<RSPCarListItem> getProductListItemClass() {
        return RSPCarListItem.class;
    }

    @Override
    public Class<RSPCarDetails> getProductDetailsClass() {
        return RSPCarDetails.class;
    }


    @Override
    public void setProductSpecificDetails(Car product) {
        product.setName(getCarName(product));
        product.setProductType(ProductType.CAR);
    }

    private static @NotNull String getCarName(Car product) {
        return product.getMake()
                + " " + product.getModel()
                + " " + product.getYear();
    }

}
