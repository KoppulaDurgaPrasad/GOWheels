package com.GoWheels.Car_Rental_System.Controller.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminProfileResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNo;
    private String role;
    private String gender;
    private LocalDate dob;
    private Integer age;
}
