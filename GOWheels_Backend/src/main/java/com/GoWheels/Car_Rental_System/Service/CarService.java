package com.GoWheels.Car_Rental_System.Service;

import com.GoWheels.Car_Rental_System.Controller.Dtos.CarRequestDto;
import com.GoWheels.Car_Rental_System.Controller.Dtos.CarResponseDto;
import com.GoWheels.Car_Rental_System.Entity.Type.FuelType;
import com.GoWheels.Car_Rental_System.Entity.Type.TransmissionType;

import java.math.BigDecimal;
import java.util.List;

public interface CarService {

    CarResponseDto addCar(
            Long adminId,
            CarRequestDto request
    );

    CarResponseDto updateCar(
            Long carId,
            CarRequestDto request
    );

    void deleteCar(Long carId);

    List<CarResponseDto> getAllCars();

    CarResponseDto getCarById(Long carId);

    List<CarResponseDto> getAvailableCars();

    List<CarResponseDto> getCarsByBrand(String brand);

    List<CarResponseDto> getCarsByFuelType(
            FuelType fuelType
    );

    List<CarResponseDto> getCarsByTransmission(
            TransmissionType transmission
    );

    List<CarResponseDto> getCarsBySeats(
            Integer seats
    );

    List<CarResponseDto> getCarsByPrice(
            BigDecimal price
    );
}