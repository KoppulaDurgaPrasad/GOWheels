package com.GoWheels.Car_Rental_System.Service;

import com.GoWheels.Car_Rental_System.Controller.Dtos.*;
import com.GoWheels.Car_Rental_System.Entity.Admin;
import com.GoWheels.Car_Rental_System.Entity.Car;
import com.GoWheels.Car_Rental_System.Entity.CarImage;
import com.GoWheels.Car_Rental_System.Entity.Type.CarStatus;
import com.GoWheels.Car_Rental_System.Entity.Type.FuelType;
import com.GoWheels.Car_Rental_System.Entity.Type.TransmissionType;
import com.GoWheels.Car_Rental_System.Repository.AdminRepository;
import com.GoWheels.Car_Rental_System.Repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final AdminRepository adminRepository;

    @Override
    @CacheEvict(value = {
            "cars",
            "availableCars"
    }, allEntries = true)
    public CarResponseDto addCar(
            Long adminId,
            CarRequestDto request
    ) {

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() ->
                        new RuntimeException("Admin not found"));

        Car car = new Car();

        car.setCarBrand(request.getCarBrand());
        car.setCarModel(request.getCarModel());
        car.setManufacturedYear(request.getManufacturedYear());
        car.setPricePerDay(request.getPricePerDay());
        car.setFuelType(request.getFuelType());
        car.setTransmission(request.getTransmission());
        car.setCategory(request.getCategory());
        car.setSeats(request.getSeats());
        car.setCarStatus(request.getCarStatus());
        car.setDescription(request.getDescription());
        car.setAdmin(admin);
        Car savedCar = carRepository.save(car);
        return mapToDto(savedCar);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "car", key = "#carId"),
            @CacheEvict(value = "cars", allEntries = true),
            @CacheEvict(value = "availableCars", allEntries = true)
    })
    public CarResponseDto updateCar(
            Long carId,
            CarRequestDto request
    ) {

        Car car = carRepository.findById(carId)
                .orElseThrow(() ->
                        new RuntimeException("Car not found"));

        car.setCarBrand(request.getCarBrand());
        car.setCarModel(request.getCarModel());
        car.setManufacturedYear(request.getManufacturedYear());
        car.setPricePerDay(request.getPricePerDay());
        car.setFuelType(request.getFuelType());
        car.setTransmission(request.getTransmission());
        car.setCategory(request.getCategory());
        car.setSeats(request.getSeats());
        car.setCarStatus(request.getCarStatus());
        car.setDescription(request.getDescription());

        Car updatedCar = carRepository.save(car);

        return mapToDto(updatedCar);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "car", key = "#carId"),
            @CacheEvict(value = "cars", allEntries = true),
            @CacheEvict(value = "availableCars", allEntries = true)
    })
    public void deleteCar(Long carId) {

        Car car = carRepository.findById(carId)
                .orElseThrow(() ->
                        new RuntimeException("Car not found"));

        carRepository.delete(car);
    }

    @Override
    @Cacheable(value = "cars")
    public List<CarResponseDto> getAllCars() {

        return carRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "car", key = "#carId")
    public CarResponseDto getCarById(Long carId) {

        Car car = carRepository.findById(carId)
                .orElseThrow(() ->
                        new RuntimeException("Car not found"));

        return mapToDto(car);
    }

    @Override
    @Cacheable(value = "availableCars")
    public List<CarResponseDto> getAvailableCars() {

        return carRepository.findAll()
                .stream()
                .filter(car ->
                        car.getCarStatus() == CarStatus.AVAILABLE)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<CarResponseDto> getCarsByBrand(String brand) {

        return carRepository.findAll()
                .stream()
                .filter(car ->
                        car.getCarBrand()
                                .equalsIgnoreCase(brand))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CarResponseDto> getCarsByFuelType(
            FuelType fuelType
    ) {

        return carRepository.findAll()
                .stream()
                .filter(car ->
                        car.getFuelType() == fuelType)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CarResponseDto> getCarsByTransmission(
            TransmissionType transmission
    ) {

        return carRepository.findAll()
                .stream()
                .filter(car ->
                        car.getTransmission() == transmission)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CarResponseDto> getCarsBySeats(
            Integer seats
    ) {

        return carRepository.findAll()
                .stream()
                .filter(car ->
                        car.getSeats().equals(seats))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CarResponseDto> getCarsByPrice(
            BigDecimal price
    ) {

        return carRepository.findAll()
                .stream()
                .filter(car ->
                        car.getPricePerDay()
                                .compareTo(price) <= 0)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private CarResponseDto mapToDto(Car car) {

        CarResponseDto dto = new CarResponseDto();

        dto.setId(car.getId());
        dto.setCarBrand(car.getCarBrand());
        dto.setCarModel(car.getCarModel());
        dto.setManufacturedYear(car.getManufacturedYear());
        dto.setPricePerDay(car.getPricePerDay());
        dto.setFuelType(car.getFuelType());
        dto.setTransmission(car.getTransmission());
        dto.setCategory(car.getCategory());
        dto.setSeats(car.getSeats());
        dto.setCarStatus(car.getCarStatus());
        dto.setDescription(car.getDescription());
        List<CarImageResponseDto> images;

        if (car.getImages() != null) {

            images = car.getImages()
                    .stream()
                    .map(this::mapImageToDto)
                    .collect(Collectors.toList());

        } else {

            images = Collections.emptyList();
        }

        dto.setImages(images);

        return dto;
    }

    private CarImageResponseDto mapImageToDto(
            CarImage image
    ) {

        CarImageResponseDto dto =
                new CarImageResponseDto();

        dto.setId(image.getId());
        dto.setImageUrl(image.getImageUrl());

        return dto;
    }
}