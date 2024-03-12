package com.n2o.tombile.controller;

import com.n2o.tombile.model.Car;
import com.n2o.tombile.service.CarService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    @GetMapping("")
    public List<Car> getAllCars(){
        return carService.getAllCars();
    }
}
