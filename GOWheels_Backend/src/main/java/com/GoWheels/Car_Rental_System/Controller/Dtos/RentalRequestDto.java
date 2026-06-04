package com.GoWheels.Car_Rental_System.Controller.Dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RentalRequestDto {

    private Long carId;
    private LocalDate startDate;
    private LocalDate endDate;
}