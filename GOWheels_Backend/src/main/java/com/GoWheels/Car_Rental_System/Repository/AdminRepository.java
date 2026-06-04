package com.GoWheels.Car_Rental_System.Repository;

import com.GoWheels.Car_Rental_System.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long> {

    Optional<Admin> findByEmail(String email);
}
