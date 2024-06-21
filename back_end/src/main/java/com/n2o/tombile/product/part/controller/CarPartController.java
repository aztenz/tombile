package com.n2o.tombile.product.part.controller;

import com.n2o.tombile.product.part.dto.RSPPartDetails;
import com.n2o.tombile.product.part.dto.RQPersistPart;
import com.n2o.tombile.product.part.dto.RSPPersistPart;
import com.n2o.tombile.product.part.service.CarPartService;
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
@RequestMapping("/parts")
@RequiredArgsConstructor
public class CarPartController {
    private final CarPartService carPartService;

    @GetMapping
    public ResponseEntity<List<Object>> getAllCarParts() {
        List<Object> CarParts = carPartService.getAll();
        return CarParts.isEmpty()? ResponseEntity.noContent().build() : ResponseEntity.ok(CarParts);
    }

    @GetMapping("/{partId}")
    public ResponseEntity<RSPPartDetails> getCarPartById(@PathVariable int partId) {
        return ResponseEntity.ok((RSPPartDetails) carPartService.getItemById(partId));
    }

    @PostMapping
    public ResponseEntity<RSPPersistPart> addCarPart(@Valid @RequestBody RQPersistPart request){
        RSPPersistPart response = (RSPPersistPart) carPartService.addItem(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{partId}")
    public ResponseEntity<RSPPersistPart> editCarPart(
            @PathVariable int partId,
            @Valid @RequestBody RQPersistPart request
    ){
        return ResponseEntity.ok((RSPPersistPart) carPartService.editItem(request, partId));
    }

    @DeleteMapping("/{partId}")
    public void deleteCarPart(
            @PathVariable int partId
    ) {
        carPartService.deleteItem(partId);
    }
}