package com.n2o.tombile.product.car.controller;

import com.n2o.tombile.image.ImageService;
import com.n2o.tombile.product.car.dto.RQPersistCar;
import com.n2o.tombile.product.car.dto.RSPCarDetails;
import com.n2o.tombile.product.car.dto.RSPPersistCar;
import com.n2o.tombile.product.car.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<List<Object>> getAllCars() {
        List<Object> cars = carService.getAll();
        return cars.isEmpty()? ResponseEntity.noContent().build() : ResponseEntity.ok(cars);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<RSPCarDetails> getCarById(@PathVariable int carId) {
        return ResponseEntity.ok((RSPCarDetails) carService.getItemById(carId));
    }

    @PostMapping
    public ResponseEntity<RSPPersistCar> addCar(@Valid @ModelAttribute RQPersistCar request){
        String imagePath = imageService.saveImageWithPath(request.getImage());

        RSPPersistCar response = (RSPPersistCar) carService.addItem(request, imagePath);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{carId}")
    public ResponseEntity<RSPPersistCar> editCar(
            @PathVariable int carId,
            @Valid @RequestBody RQPersistCar request
    ){
        return ResponseEntity.ok((RSPPersistCar) carService.editItem(request, carId));
    }

    @DeleteMapping("/{carId}")
    public void deleteCar(
            @PathVariable int carId
    ) {
        carService.deleteItem(carId);
    }
}
