package com.GoWheels.Car_Rental_System.Repository;

import com.GoWheels.Car_Rental_System.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findByEmail(String email);
    boolean existsByEmail(String email);


}
