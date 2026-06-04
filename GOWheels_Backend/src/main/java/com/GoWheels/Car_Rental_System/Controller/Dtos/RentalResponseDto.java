package com.GoWheels.Car_Rental_System.Controller.Dtos;

import com.GoWheels.Car_Rental_System.Entity.Type.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalResponseDto implements Serializable {

    private Long rentalId;
    private Long carId;
    private String carBrand;
    private String carModel;
    private String imageUrl;
    private Long customerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalAmount;
    private RentalStatus status;
    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;
    private String customerPhone;
    private String gender;
    private Integer age;
    private String dob;
    private String role;
    private String licenseNumber;
    private String identityProofType;
    private String identityProofNumber;
    private String documentUrl;
}