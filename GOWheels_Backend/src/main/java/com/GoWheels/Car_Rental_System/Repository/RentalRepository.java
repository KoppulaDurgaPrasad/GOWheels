package com.GoWheels.Car_Rental_System.Repository;

import com.GoWheels.Car_Rental_System.Entity.Customer;
import com.GoWheels.Car_Rental_System.Entity.Rental;
import com.GoWheels.Car_Rental_System.Entity.Type.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByCustomer(Customer customer);

    long countByCustomerAndStatusIn(
            Customer customer,
            List<RentalStatus> statuses
    );

    List<Rental> findByStatus(RentalStatus status);
}