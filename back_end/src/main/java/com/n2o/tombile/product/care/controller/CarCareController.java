package com.n2o.tombile.product.care.controller;

import com.n2o.tombile.product.care.dto.CarCareDetails;
import com.n2o.tombile.product.care.dto.PersistCarCareRQ;
import com.n2o.tombile.product.care.dto.PersistCarCareRSP;
import com.n2o.tombile.product.care.service.CarCareService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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