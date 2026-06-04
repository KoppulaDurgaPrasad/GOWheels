package com.GoWheels.Car_Rental_System.Controller.Dtos;

import com.GoWheels.Car_Rental_System.Entity.Type.IdentityProofType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailsDto implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String contactNo;
    private String licenseNumber;
    private IdentityProofType identityProofType;
    private String identityProofNumber;
    private List<CustomerDocumentResponse> documents;
}