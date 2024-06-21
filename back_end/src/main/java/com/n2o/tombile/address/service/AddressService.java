package com.n2o.tombile.address.service;

import com.n2o.tombile.address.dto.RQAddAddress;
import com.n2o.tombile.address.dto.RQEditAddress;
import com.n2o.tombile.address.dto.RSPAddressDetails;
import com.n2o.tombile.address.dto.RSPAddressListItem;
import com.n2o.tombile.address.dto.RSPPersistAddress;
import com.n2o.tombile.address.model.Address;
import com.n2o.tombile.address.repository.AddressRepository;
import com.n2o.tombile.core.common.exception.ItemNotFoundException;
import com.n2o.tombile.core.common.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.n2o.tombile.core.common.util.Constants.ERROR_ADDRESS_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public RSPPersistAddress addAddress(RQAddAddress request) {
        Address address = Util.cloneObject(request, Address.class);

        address.setUser(Util.getCurrentUser());

        address = addressRepository.save(address);

        return Util.cloneObject(address, RSPPersistAddress.class);
    }

    public RSPPersistAddress editAddress(RQEditAddress request, int id) {
        Address address = getUserAddress(id);

        Util.copyProperties(request, address);

        addressRepository.save(address);

        return Util.cloneObject(address, RSPPersistAddress.class);
    }

    public List<RSPAddressListItem> getAllAddresses() {
        List<Address> addresses = getAllUserAddresses();

        List<RSPAddressListItem> responses = new ArrayList<>();

        addresses.forEach(address -> responses.add(Util.cloneObject(address, RSPAddressListItem.class)));

        return responses;
    }

    public RSPAddressDetails getAddressById(int id) {
        Address address = getUserAddress(id);

        return Util.cloneObject(address, RSPAddressDetails.class);
    }

    public void deleteAddress(int id) {
        addressRepository
                .findAddressByIdAndUserId(id, Util.getCurrentUserId())
                .ifPresent(addressRepository::delete);
    }

    private List<Address> getAllUserAddresses() {
        return addressRepository.findAddressByUserId(Util.getCurrentUserId());
    }

    private Address getUserAddress(int id) {
        return addressRepository.findAddressByIdAndUserId(id, Util.getCurrentUserId())
                .orElseThrow(() -> new ItemNotFoundException(ERROR_ADDRESS_NOT_FOUND));
    }
}
