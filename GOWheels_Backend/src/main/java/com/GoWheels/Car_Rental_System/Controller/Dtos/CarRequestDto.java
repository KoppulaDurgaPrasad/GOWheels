package com.GoWheels.Car_Rental_System.Controller.Dtos;

import com.GoWheels.Car_Rental_System.Entity.Type.CarStatus;
import com.GoWheels.Car_Rental_System.Entity.Type.Category;
import com.GoWheels.Car_Rental_System.Entity.Type.FuelType;
import com.GoWheels.Car_Rental_System.Entity.Type.TransmissionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarRequestDto {

    private String carBrand;
    private String carModel;
    private Integer manufacturedYear;
    private BigDecimal pricePerDay;
    private FuelType fuelType;
    private TransmissionType transmission;
    private Category category;
    private Integer seats;
    private CarStatus carStatus;
    private String description;
}