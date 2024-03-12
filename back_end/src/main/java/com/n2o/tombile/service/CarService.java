package com.n2o.tombile.service;

import com.n2o.tombile.dto.request.car.CarDTO;
import com.n2o.tombile.model.Car;
import com.n2o.tombile.model.Product;
import com.n2o.tombile.repository.CarRepository;
import com.n2o.tombile.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CarService {
    private final CarRepository carRepository;
    private final ProductRepository productRepository;

    public void addCar(CarDTO carDTO){
        Product product = Product.builder()
                .productType("CAR")
                .supplierId(1)
                .name(carDTO.getName())
                .description(carDTO.getDescription())
                .price(carDTO.getPrice())
                .build();

        Product savedProduct = productRepository.save(product);

        Car car = Car.builder()
                .id(savedProduct.getId())
                .carState(carDTO.getCarState())
                .make(carDTO.getMake())
                .model(carDTO.getModel())
                .mileage(carDTO.getMileage())
                .year(carDTO.getYear())
                .build();
        carRepository.save(car);
    }

    public Car getCarById(int id){
        return carRepository.findById(id).orElseThrow();
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car editCar(Car request){
        Car car = carRepository.findById(request.getId()).orElseThrow();
        car.setCarState(request.getCarState());
        car.setMake(request.getMake());
        car.setModel(request.getModel());
        car.setMileage(request.getMileage());
        return car;
    }

    public void deleteCar(int id) {
        Car carToDelete = carRepository.findById(id).orElseThrow();
        carRepository.delete(carToDelete);
    }
}
