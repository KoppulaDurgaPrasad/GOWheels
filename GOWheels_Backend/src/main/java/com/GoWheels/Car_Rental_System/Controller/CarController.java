package com.GoWheels.Car_Rental_System.Controller;


import com.GoWheels.Car_Rental_System.Controller.Dtos.CarImageRequestDto;
import com.GoWheels.Car_Rental_System.Controller.Dtos.CarRequestDto;
import com.GoWheels.Car_Rental_System.Controller.Dtos.CarResponseDto;
import com.GoWheels.Car_Rental_System.Entity.Admin;
import com.GoWheels.Car_Rental_System.Entity.CarImage;
import com.GoWheels.Car_Rental_System.Entity.Type.FuelType;
import com.GoWheels.Car_Rental_System.Entity.Type.TransmissionType;
import com.GoWheels.Car_Rental_System.Service.AdminService;
import com.GoWheels.Car_Rental_System.Service.CarImageService;
import com.GoWheels.Car_Rental_System.Service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final CarImageService carImageService;
    private final AdminService adminService;

    private String getEmail() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    @PostMapping(
            value = "/admin/cars",
            consumes = {
                    "multipart/form-data"
            }
    )
    public ResponseEntity<CarResponseDto> addCar(
            @RequestPart("car")
            CarRequestDto request,
            @RequestPart(
                    value = "images",
                    required = false
            )
            List<MultipartFile> images
    ) {

        Admin admin =
                adminService.getByEmail(
                        getEmail()
                );

        CarResponseDto car =
                carService.addCar(
                        admin.getId(),
                        request
                );
        if (images != null) {
            for (MultipartFile file : images) {
                carImageService.uploadImage(
                        car.getId(),
                        file
                );
            }
        }
        return ResponseEntity.ok(car);
    }

    @PutMapping(
            value = "/admin/cars/{carId}",
            consumes = {
                    "multipart/form-data"
            }
    )
    public ResponseEntity<CarResponseDto> updateCar(

            @PathVariable Long carId,

            @RequestPart("car")
            CarRequestDto request,

            @RequestPart(
                    value = "images",
                    required = false
            )
            List<MultipartFile> images
    ) {

        CarResponseDto updated =
                carService.updateCar(
                        carId,
                        request
                );
        if (images != null) {

            for (MultipartFile file : images) {

                carImageService.uploadImage(
                        updated.getId(),
                        file
                );
            }
        }

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/admin/cars/{carId}")
    public ResponseEntity<String> deleteCar(
            @PathVariable Long carId
    ) {

        carService.deleteCar(carId);

        return ResponseEntity.ok("Car deleted successfully");
    }

    @GetMapping("/cars")
    public ResponseEntity<List<CarResponseDto>>
    getAllCars() {

        return ResponseEntity.ok(
                carService.getAllCars()
        );
    }

    @GetMapping("/cars/{carId}")
    public ResponseEntity<CarResponseDto>
    getCarById(
            @PathVariable Long carId
    ) {

        return ResponseEntity.ok(
                carService.getCarById(carId)
        );
    }

    @GetMapping("/cars/available")
    public ResponseEntity<List<CarResponseDto>>
    getAvailableCars() {

        return ResponseEntity.ok(
                carService.getAvailableCars()
        );
    }

    @GetMapping("/cars/filter/brand")
    public ResponseEntity<List<CarResponseDto>>
    getCarsByBrand(
            @RequestParam String brand
    ) {

        return ResponseEntity.ok(
                carService.getCarsByBrand(brand)
        );
    }

    @GetMapping("/cars/filter/fuel")
    public ResponseEntity<List<CarResponseDto>>
    getCarsByFuel(
            @RequestParam FuelType fuelType
    ) {

        return ResponseEntity.ok(
                carService.getCarsByFuelType(fuelType)
        );
    }

    @GetMapping("/cars/filter/transmission")
    public ResponseEntity<List<CarResponseDto>>
    getCarsByTransmission(
            @RequestParam
            TransmissionType transmission
    ) {

        return ResponseEntity.ok(
                carService.getCarsByTransmission(
                        transmission
                )
        );
    }

    @GetMapping("/cars/filter/seats")
    public ResponseEntity<List<CarResponseDto>>
    getCarsBySeats(
            @RequestParam Integer seats
    ) {

        return ResponseEntity.ok(
                carService.getCarsBySeats(seats)
        );
    }

    @GetMapping("/cars/filter/price")
    public ResponseEntity<List<CarResponseDto>>
    getCarsByPrice(
            @RequestParam BigDecimal price
    ) {

        return ResponseEntity.ok(
                carService.getCarsByPrice(price)
        );
    }
}
