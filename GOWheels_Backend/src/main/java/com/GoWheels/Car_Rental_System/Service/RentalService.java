package com.GoWheels.Car_Rental_System.Service;


import com.GoWheels.Car_Rental_System.Controller.Dtos.RentalRequestDto;
import com.GoWheels.Car_Rental_System.Controller.Dtos.RentalResponseDto;
import com.GoWheels.Car_Rental_System.Entity.Type.RentalStatus;

import java.util.List;

public interface RentalService {

    RentalResponseDto rentCar(
            Long customerId,
            RentalRequestDto request
    );

    RentalResponseDto updateRentalStatus(
            Long rentalId,
            RentalStatus status
    );

    List<RentalResponseDto> getCustomerRentals(Long customerId);

    List<RentalResponseDto> getAllRentals();

    List<RentalResponseDto> getRentalsByStatus(
            RentalStatus status
    );
}
