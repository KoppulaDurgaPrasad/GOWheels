package com.GoWheels.Car_Rental_System.Service;

import com.GoWheels.Car_Rental_System.Controller.Dtos.AdminProfileRequest;
import com.GoWheels.Car_Rental_System.Controller.Dtos.CustomerDetailsDto;
import com.GoWheels.Car_Rental_System.Entity.Admin;

import java.util.Optional;

public interface AdminService {

    Admin save(Admin admin);
    Admin loginWithGoogle(String email, String providerId);
    Admin login(String email, String password);
    Admin updateProfile(Long adminId, AdminProfileRequest request);
    Admin getByEmail(String email);
    CustomerDetailsDto getCustomerDetails(Long rentalId);

}