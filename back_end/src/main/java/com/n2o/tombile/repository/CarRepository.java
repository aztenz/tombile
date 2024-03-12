package com.n2o.tombile.repository;

import com.n2o.tombile.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
}
