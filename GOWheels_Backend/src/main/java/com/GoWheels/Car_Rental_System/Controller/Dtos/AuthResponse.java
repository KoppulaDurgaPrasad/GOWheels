package com.GoWheels.Car_Rental_System.Controller.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private Long id;
    private String email;
    private String role;
    private String token;
}
