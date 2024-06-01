package com.n2o.tombile.repository;

import com.n2o.tombile.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
