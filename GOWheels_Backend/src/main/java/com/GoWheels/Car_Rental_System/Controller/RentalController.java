package com.GoWheels.Car_Rental_System.Controller;

import com.GoWheels.Car_Rental_System.Controller.Dtos.RentalRequestDto;
import com.GoWheels.Car_Rental_System.Controller.Dtos.RentalResponseDto;
import com.GoWheels.Car_Rental_System.Entity.Customer;
import com.GoWheels.Car_Rental_System.Entity.Type.RentalStatus;
import com.GoWheels.Car_Rental_System.Service.CustomerService;
import com.GoWheels.Car_Rental_System.Service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;
    private final CustomerService customerService;

    private String getEmail() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    @PostMapping("/customer/rent")
    public ResponseEntity<RentalResponseDto> rentCar(
            @RequestBody RentalRequestDto request
    ) {

        Customer customer =
                customerService.getByEmail(getEmail());

        return ResponseEntity.ok(
                rentalService.rentCar(
                        customer.getId(),
                        request
                )
        );
    }

    @GetMapping("/customer/rentals")
    public ResponseEntity<List<RentalResponseDto>>
    getCustomerRentals() {

        Customer customer =
                customerService.getByEmail(getEmail());

        return ResponseEntity.ok(
                rentalService.getCustomerRentals(
                        customer.getId()
                )
        );
    }

    @GetMapping("/admin/rentals")
    public ResponseEntity<List<RentalResponseDto>>
    getAllRentals() {

        return ResponseEntity.ok(
                rentalService.getAllRentals()
        );
    }

    @GetMapping("/admin/rentals/status")
    public ResponseEntity<List<RentalResponseDto>>
    getRentalsByStatus(
            @RequestParam RentalStatus status
    ) {

        return ResponseEntity.ok(
                rentalService.getRentalsByStatus(status)
        );
    }

    @PutMapping("/admin/rentals/{rentalId}")
    public ResponseEntity<RentalResponseDto>
    updateRentalStatus(
            @PathVariable Long rentalId,
            @RequestParam RentalStatus status
    ) {

        return ResponseEntity.ok(
                rentalService.updateRentalStatus(
                        rentalId,
                        status
                )
        );
    }
}
