package com.n2o.tombile.controller;

import com.n2o.tombile.dto.request.product.PersistPartRQ;
import com.n2o.tombile.dto.response.product.part.PartDetails;
import com.n2o.tombile.dto.response.product.part.PersistPartRSP;
import com.n2o.tombile.service.product.CarPartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/parts")
public class CarPartController {
    private final CarPartService carPartService;

    @GetMapping
    public ResponseEntity<List<Object>> getAllCarParts() {
        List<Object> CarParts = carPartService.getAll();
        return CarParts.isEmpty()? ResponseEntity.noContent().build() : ResponseEntity.ok(CarParts);
    }

    @GetMapping("/{partId}")
    public ResponseEntity<PartDetails> getCarPartById(@PathVariable int partId) {
        return ResponseEntity.ok((PartDetails) carPartService.getItemById(partId));
    }

    @PostMapping
    public ResponseEntity<PersistPartRSP> addCarPart(@Valid @RequestBody PersistPartRQ request){
        PersistPartRSP persistPartRSP = (PersistPartRSP) carPartService.addItem(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(persistPartRSP.getId())
                .toUri();

        return ResponseEntity.created(location).body(persistPartRSP);
    }

    @PutMapping("/{partId}")
    public ResponseEntity<PersistPartRSP> editCarPart(
            @PathVariable int partId,
            @Valid @RequestBody PersistPartRQ request
    ){
        return ResponseEntity.ok((PersistPartRSP) carPartService.editItem(request, partId));
    }

    @DeleteMapping("/{partId}")
    public void deleteCarPart(
            @PathVariable int partId
    ) {
        carPartService.deleteItem(partId);
    }
}