package com.n2o.tombile.service;

import com.n2o.tombile.dto.request.car.PostCarRQ;
import com.n2o.tombile.dto.request.car.PutCarRQ;
import com.n2o.tombile.dto.response.car.CarDetails;
import com.n2o.tombile.dto.response.car.CarListItem;
import com.n2o.tombile.dto.response.car.PostCarRSP;
import com.n2o.tombile.dto.response.car.PutCarRSP;
import com.n2o.tombile.enums.ProductType;
import com.n2o.tombile.model.Car;
import com.n2o.tombile.repository.CarRepository;
import com.n2o.tombile.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService extends ProductService{
    private final CarRepository carRepository;

    public CarService(
            CarRepository carRepository,
            ProductRepository productRepository
    ) {
        super(productRepository);
        this.carRepository = carRepository;
    }


    public PostCarRSP addCar(PostCarRQ postCarRQ) {
        return super.saveProduct(
                PostCarRSP.class,
                Car.class,
                postCarRQ.getMake()
                        +" "+postCarRQ.getModel()
                        +" "+postCarRQ.getYear(),
                ProductType.CAR,
                postCarRQ
        );
    }

    public CarDetails getCarById(int id) {
        return super.getProductById(
                CarDetails.class,
                id,
                carRepository
        );
    }

    public List<CarListItem> getAllCars() {
        return super.getAllProducts(
                CarListItem.class,
                carRepository
        );
    }

    public PutCarRSP editCar(
            int id,
            PutCarRQ putCarRQ
    ) {
        return super.editProduct(
                PutCarRSP.class,
                Car.class,
                putCarRQ,
                id,
                carRepository
        );
    }

    public void deleteCar(int id) {
        super.deleteProduct(id, carRepository);
    }
}
