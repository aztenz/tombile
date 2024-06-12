package com.n2o.tombile.address.repository;

import com.n2o.tombile.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    Optional<Address> findAddressByIdAndUserId(int id, int userId);
    List<Address> findAddressByUserId(int userId);
}
