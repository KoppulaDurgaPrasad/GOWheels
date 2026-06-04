package com.GoWheels.Car_Rental_System.Controller.Dtos;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
