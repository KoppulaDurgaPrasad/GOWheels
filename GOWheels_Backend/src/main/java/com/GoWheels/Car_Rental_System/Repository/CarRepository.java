package com.GoWheels.Car_Rental_System.Repository;

import com.GoWheels.Car_Rental_System.Entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

}