package com.n2o.tombile.controller;

import com.n2o.tombile.dto.request.product.PersistCarCareRQ;
import com.n2o.tombile.dto.response.product.car_care.CarCareDetails;
import com.n2o.tombile.dto.response.product.car_care.PersistCarCareRSP;
import com.n2o.tombile.service.product.CarCareService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/Cares")
@RequiredArgsConstructor
public class CarCareController {
    private final CarCareService carCareService;

    @GetMapping
    public ResponseEntity<List<Object>> getAllCarCares() {
        List<Object> CarCares = carCareService.getAll();
        return CarCares.isEmpty()? ResponseEntity.noContent().build() : ResponseEntity.ok(CarCares);
    }

    @GetMapping("/{carCareId}")
    public ResponseEntity<CarCareDetails> getCarCareById(@PathVariable int carCareId) {
        return ResponseEntity.ok((CarCareDetails) carCareService.getItemById(carCareId));
    }

    @PostMapping
    public ResponseEntity<PersistCarCareRSP> addCarCare(@Valid @RequestBody PersistCarCareRQ request){
        PersistCarCareRSP persistCarCareRSP = (PersistCarCareRSP) carCareService.addItem(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(persistCarCareRSP.getId())
                .toUri();

        return ResponseEntity.created(location).body(persistCarCareRSP);
    }

    @PutMapping("/{carCareId}")
    public ResponseEntity<PersistCarCareRSP> editCarCare(
            @PathVariable int carCareId,
            @Valid @RequestBody PersistCarCareRQ request
    ){
        return ResponseEntity.ok((PersistCarCareRSP) carCareService.editItem(request, carCareId));
    }

    @DeleteMapping("/{carCareId}")
    public void deleteCarCare(
            @PathVariable int carCareId
    ) {
        carCareService.deleteItem(carCareId);
    }
}