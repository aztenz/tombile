package com.n2o.tombile.address.service;

import com.n2o.tombile.address.dto.*;
import com.n2o.tombile.address.model.Address;
import com.n2o.tombile.address.repository.AddressRepository;
import com.n2o.tombile.core.common.exception.ItemNotFoundException;
import com.n2o.tombile.core.common.util.Util;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {
    private static final String ADDRESS_NOT_FOUND = "couldn't find requested address for the given user";

    private final AddressRepository addressRepository;

    public RSPPersistAddress addAddress(RQAddAddress request) {
        Address address = Util.cloneObject(request, Address.class);

        addressRepository.save(address);

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

        List<RSPAddressListItem> addressListItems = new ArrayList<>();

        addresses.forEach(address -> addressListItems.add(Util.cloneObject(address, RSPAddressListItem.class)));

        return addressListItems;
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
                .orElseThrow(() -> new ItemNotFoundException(ADDRESS_NOT_FOUND));
    }
}
