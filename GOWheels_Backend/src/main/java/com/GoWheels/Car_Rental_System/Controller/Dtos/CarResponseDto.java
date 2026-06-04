package com.GoWheels.Car_Rental_System.Controller.Dtos;

import com.GoWheels.Car_Rental_System.Entity.Type.CarStatus;
import com.GoWheels.Car_Rental_System.Entity.Type.Category;
import com.GoWheels.Car_Rental_System.Entity.Type.FuelType;
import com.GoWheels.Car_Rental_System.Entity.Type.TransmissionType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CarResponseDto implements Serializable {

    private Long id;
    private String carBrand;
    private String carModel;
    private Integer manufacturedYear;
    private BigDecimal pricePerDay;
    private FuelType fuelType;
    private TransmissionType transmission;
    private Category category;
    private Integer seats;
    private CarStatus carStatus;
    private List<CarImageResponseDto> images;
    private String description;
}