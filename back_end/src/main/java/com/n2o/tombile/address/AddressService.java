package com.n2o.tombile.address;

import com.n2o.tombile.core.common.service.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddressService
        implements CRUDService<Address, Integer, AddressRepository> {

    private final AddressRepository addressRepository;

    @Override
    public AddressRepository getRepository() {
        return addressRepository;
    }

    @Override
    public Class<Address> getEntity() {
        return Address.class;
    }

    @Override
    public Class<?> getPersistRSP() {
        return null;
    }

    @Override
    public Class<?> getListItem() {
        return null;
    }

    @Override
    public Class<?> getDetails() {
        return null;
    }
}
