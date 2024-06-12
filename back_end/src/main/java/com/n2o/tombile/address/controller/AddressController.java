package com.n2o.tombile.address.controller;


import com.n2o.tombile.address.dto.*;
import com.n2o.tombile.address.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<RSPPersistAddress> addAddress(
            @Valid @RequestBody RQAddAddress request
    ) {
        RSPPersistAddress rspPersistAddress = addressService.addAddress(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(rspPersistAddress.getId())
                .toUri();

        return ResponseEntity.created(location).body(rspPersistAddress);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RSPPersistAddress> editAddress(
            @PathVariable int id,
            @Valid @RequestBody RQEditAddress request
    ) {
        return ResponseEntity.ok(addressService.editAddress(request, id));
    }

    @GetMapping
    public ResponseEntity<List<RSPAddressListItem>> getAllAddresses() {
        List<RSPAddressListItem> addresses = addressService.getAllAddresses();
        return addresses.isEmpty()? ResponseEntity.noContent().build() : ResponseEntity.ok(addresses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RSPAddressDetails> getAddressById(
            @PathVariable int id
    ) {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(
            @PathVariable int id
    ) { addressService.deleteAddress(id); }

}
