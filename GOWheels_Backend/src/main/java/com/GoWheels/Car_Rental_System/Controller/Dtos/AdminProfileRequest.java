package com.GoWheels.Car_Rental_System.Controller.Dtos;

import com.GoWheels.Car_Rental_System.Entity.Type.GenderType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AdminProfileRequest {

    private String firstName;
    private String lastName;
    private GenderType gender;
    private LocalDate dob;
    private Integer age;
    private String contactNo;
    private String password;
    private String confirmPassword;
}
