package com.GoWheels.Car_Rental_System.Controller.Dtos;

import com.GoWheels.Car_Rental_System.Entity.Type.GenderType;
import com.GoWheels.Car_Rental_System.Entity.Type.IdentityProofType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProfileRequest {

    private String firstName;
    private String lastName;
    private GenderType gender;
    private LocalDate dob;
    private String contactNo;
    private String licenseNumber;
    private IdentityProofType identityProofType;
    private String identityProofNumber;
    private String password;
    private String confirmPassword;
}
