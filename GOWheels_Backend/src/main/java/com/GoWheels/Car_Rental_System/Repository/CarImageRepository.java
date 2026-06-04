package com.GoWheels.Car_Rental_System.Repository;

import com.GoWheels.Car_Rental_System.Entity.CarImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarImageRepository extends JpaRepository<CarImage, Long> {
}