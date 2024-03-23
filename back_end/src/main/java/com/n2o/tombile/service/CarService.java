package com.n2o.tombile.service;

import com.n2o.tombile.dto.request.car.PostCarRQ;
import com.n2o.tombile.dto.response.car.CarDetails;
import com.n2o.tombile.dto.response.car.CarListItem;
import com.n2o.tombile.dto.response.car.PostCarRSP;
import com.n2o.tombile.enums.ProductType;
import com.n2o.tombile.exception.ItemNotFoundException;
import com.n2o.tombile.model.Car;
import com.n2o.tombile.model.User;
import com.n2o.tombile.repository.CarRepository;
import com.n2o.tombile.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CarService {
    private static final String CAR_NOT_FOUND = "car not found";
    private final CarRepository carRepository;
    private final ProductRepository productRepository;

    public PostCarRSP addCar(PostCarRQ postCarRQ) {
        int supplierId = getCurrentUserId();
        Car car = createCar(postCarRQ, supplierId);
        productRepository.save(car);
        return createPostCarRSP(car);
    }

    public CarDetails getCarById(int id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(CAR_NOT_FOUND));
        return toCarDetails(car);
    }

    public List<CarListItem> getAllCars() {
        List<Car> cars = carRepository.findAll();
        List<CarListItem> carListItems = new ArrayList<>();
        cars.forEach(car -> carListItems.add(toCarListItem(car)));
        return carListItems;
    }

    public Car editCar(Car request) {
        Car car = carRepository.findById(request.getId()).orElseThrow();
        car.setCarState(request.getCarState());
        car.setMake(request.getMake());
        car.setModel(request.getModel());
        car.setMileage(request.getMileage());
        return car;
    }

    public void deleteCar(int id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(CAR_NOT_FOUND));
        carRepository.delete(car);
    }

    private int getCurrentUserId() {
        return ((User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getId();
    }

    private Car createCar(
            PostCarRQ postCarRQ,
            int supplierId
    ) {
        return Car.builder()
                .make(postCarRQ.getMake())
                .model(postCarRQ.getModel())
                .year(postCarRQ.getYear())
                .mileage(postCarRQ.getMileage())
                .carState(postCarRQ.getCarState())
                .supplierId(supplierId)
                .name(postCarRQ.getMake()+" "+postCarRQ.getModel()+" "+postCarRQ.getYear())
                .description(postCarRQ.getDescription())
                .price(postCarRQ.getPrice())
                .productType(ProductType.CAR)
                .build();
    }

    private PostCarRSP createPostCarRSP(Car car) {
        return PostCarRSP.builder()
                .id(car.getId())
                .supplierId(car.getSupplierId())
                .name(car.getName())
                .description(car.getDescription())
                .price(car.getPrice())
                .carState(car.getCarState())
                .make(car.getMake())
                .model(car.getModel())
                .mileage(car.getMileage())
                .year(car.getYear())
                .build();
    }

    private CarListItem toCarListItem(Car car) {
        return CarListItem.builder()
                .id(car.getId())
                .name(car.getName())
                .price(car.getPrice())
                .carState(car.getCarState())
                .build();
    }

    private CarDetails toCarDetails(Car car) {
        CarDetails carDetails = new CarDetails();
        BeanUtils.copyProperties(car, carDetails);
        return carDetails;
    }
}
