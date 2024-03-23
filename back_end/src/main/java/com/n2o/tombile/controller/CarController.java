package com.n2o.tombile.controller;

import com.n2o.tombile.dto.request.car.PostCarRQ;
import com.n2o.tombile.dto.response.car.CarDetails;
import com.n2o.tombile.dto.response.car.CarListItem;
import com.n2o.tombile.dto.response.car.PostCarRSP;
import com.n2o.tombile.model.Car;
import com.n2o.tombile.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<CarListItem>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }
    @GetMapping("/{carId}")
    public ResponseEntity<CarDetails> getCarById(
            @PathVariable int carId
    ) {
        return ResponseEntity.ok(carService.getCarById(carId));
    }

    @PostMapping
    public ResponseEntity<PostCarRSP> addCar(
            @Valid @RequestBody PostCarRQ request
    ){
        return ResponseEntity.ok(carService.addCar(request));
    }
}
