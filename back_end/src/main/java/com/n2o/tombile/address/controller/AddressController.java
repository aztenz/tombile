package com.n2o.tombile.address.controller;

import com.n2o.tombile.address.dto.RQAddAddress;
import com.n2o.tombile.address.dto.RQEditAddress;
import com.n2o.tombile.address.dto.RSPAddressDetails;
import com.n2o.tombile.address.dto.RSPAddressListItem;
import com.n2o.tombile.address.dto.RSPPersistAddress;
import com.n2o.tombile.address.service.AddressService;
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
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<RSPPersistAddress> addAddress(
            @Valid @RequestBody RQAddAddress request
    ) {
        RSPPersistAddress response = addressService.addAddress(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
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
    public void deleteAddress( @PathVariable int id ) { addressService.deleteAddress(id); }

}
