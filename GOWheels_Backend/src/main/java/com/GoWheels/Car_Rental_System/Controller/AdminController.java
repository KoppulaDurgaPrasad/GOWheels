package com.GoWheels.Car_Rental_System.Controller;

import com.GoWheels.Car_Rental_System.Controller.Dtos.AdminProfileRequest;
import com.GoWheels.Car_Rental_System.Controller.Dtos.AdminProfileResponse;
import com.GoWheels.Car_Rental_System.Controller.Dtos.CustomerDetailsDto;
import com.GoWheels.Car_Rental_System.Entity.Admin;
import com.GoWheels.Car_Rental_System.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private String getEmail() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    @GetMapping("/profile")
    public ResponseEntity<AdminProfileResponse> getProfile() {

        Admin admin =
                adminService.getByEmail(
                        getEmail()
                );

        return ResponseEntity.ok(

                new AdminProfileResponse(
                        admin.getId(),
                        admin.getFirstName(),
                        admin.getLastName(),
                        admin.getEmail(),
                        admin.getContactNo(),
                        admin.getRole().name(),
                        admin.getGender().name(),
                        admin.getDob(),
                        admin.getAge()
                )
        );
    }

    @PutMapping("/update-profile")
    public ResponseEntity<AdminProfileResponse> updateProfile(
            @RequestBody AdminProfileRequest request) {

        Admin admin = adminService.getByEmail(getEmail());
        Admin updated = adminService.updateProfile(admin.getId(), request);

        return ResponseEntity.ok(
                new AdminProfileResponse(
                        updated.getId(),
                        updated.getFirstName(),
                        updated.getLastName(),
                        updated.getEmail(),
                        updated.getContactNo(),
                        updated.getRole().name(),
                        updated.getGender().name(),
                        updated.getDob(),
                        updated.getAge()
                )
        );
    }

    @PostMapping("/create")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        return ResponseEntity.ok(adminService.save(admin));
    }

    @GetMapping("/customer-details/{rentalId}")
    public ResponseEntity<CustomerDetailsDto> getCustomerDetails(
            @PathVariable Long rentalId) {

        return ResponseEntity.ok(
                adminService.getCustomerDetails(rentalId)
        );
    }
}