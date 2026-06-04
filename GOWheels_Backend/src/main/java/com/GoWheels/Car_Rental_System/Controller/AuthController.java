package com.GoWheels.Car_Rental_System.Controller;

import com.GoWheels.Car_Rental_System.Config.JwtUtil;
import com.GoWheels.Car_Rental_System.Controller.Dtos.AuthResponse;
import com.GoWheels.Car_Rental_System.Controller.Dtos.LoginRequest;
import com.GoWheels.Car_Rental_System.Entity.Admin;
import com.GoWheels.Car_Rental_System.Entity.Customer;
import com.GoWheels.Car_Rental_System.Service.AdminService;
import com.GoWheels.Car_Rental_System.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CustomerService customerService;
    private final AdminService adminService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        String email = request.getEmail();
        String password = request.getPassword();

        try {
            Customer c = customerService.login(email, password);

            String token = jwtUtil.generateToken(
                    c.getEmail(),
                    c.getRole().name()
            );

            return ResponseEntity.ok(
                    new AuthResponse(
                            c.getId(),
                            c.getEmail(),
                            c.getRole().name(),
                            token
                    )
            );

        } catch (RuntimeException ex1) {
            try {
                Admin a = adminService.login(email, password);

                String token = jwtUtil.generateToken(
                        a.getEmail(),
                        a.getRole().name()
                );

                return ResponseEntity.ok(
                        new AuthResponse(
                                a.getId(),
                                a.getEmail(),
                                a.getRole().name(),
                                token
                        )
                );

            } catch (RuntimeException ex2) {
                throw new RuntimeException("Invalid email or password");
            }
        }
    }
}