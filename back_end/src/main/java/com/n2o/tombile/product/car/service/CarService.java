package com.n2o.tombile.product.car.service;

import com.n2o.tombile.product.product.model.ProductType;
import com.n2o.tombile.product.car.model.Car;
import com.n2o.tombile.product.car.dto.CarDetails;
import com.n2o.tombile.product.car.dto.CarListItem;
import com.n2o.tombile.product.car.dto.PersistCarRSP;
import com.n2o.tombile.product.product.service.ProductService;
import com.n2o.tombile.product.car.repository.CarRepository;
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
