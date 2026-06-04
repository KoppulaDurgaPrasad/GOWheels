package com.GoWheels.Car_Rental_System.Controller.Dtos;

import com.GoWheels.Car_Rental_System.Entity.Type.AuthProvider;
import com.GoWheels.Car_Rental_System.Entity.Type.GenderType;
import com.GoWheels.Car_Rental_System.Entity.Type.IdentityProofType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProfileResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNo;
    private GenderType gender;
    private LocalDate dob;
    private Integer age;
    private String licenseNumber;
    private IdentityProofType identityProofType;
    private String identityProofNumber;
    private boolean isProfileComplete;
    private List<CustomerDocumentResponse> documents;
    private AuthProvider provider;
}
