package com.n2o.tombile.controller;

import com.n2o.tombile.dto.request.product.PersistCarRQ;
import com.n2o.tombile.dto.response.product.car.CarDetails;
import com.n2o.tombile.dto.response.product.car.PersistCarRSP;
import com.n2o.tombile.service.product.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<Object>> getAllCars() {
        List<Object> cars = carService.getAll();
        return cars.isEmpty()? ResponseEntity.noContent().build() : ResponseEntity.ok(cars);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<CarDetails> getCarById(@PathVariable int carId) {
        return ResponseEntity.ok((CarDetails) carService.getItemById(carId));
    }

    @PostMapping
    public ResponseEntity<PersistCarRSP> addCar(@Valid @RequestBody PersistCarRQ request){
        PersistCarRSP PersistCarRSP = (PersistCarRSP) carService.addItem(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(PersistCarRSP.getId())
                .toUri();

        return ResponseEntity.created(location).body(PersistCarRSP);
    }

    @PutMapping("/{carId}")
    public ResponseEntity<PersistCarRSP> editCar(
            @PathVariable int carId,
            @Valid @RequestBody PersistCarRQ request
    ){
        return ResponseEntity.ok((PersistCarRSP) carService.editItem(request, carId));
    }

    @DeleteMapping("/{carId}")
    public void deleteCar(
            @PathVariable int carId
    ) {
        carService.deleteItem(carId);
    }
}
