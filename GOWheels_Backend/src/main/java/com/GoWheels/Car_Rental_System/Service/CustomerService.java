package com.GoWheels.Car_Rental_System.Service;

import com.GoWheels.Car_Rental_System.Controller.Dtos.CustomerProfileRequest;
import com.GoWheels.Car_Rental_System.Entity.Customer;
import com.GoWheels.Car_Rental_System.Entity.CustomerDocument;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CustomerService {

    Customer loginWithGoogle(String email, String providerId);

    Customer login(String email, String password);

    Customer completeProfile(Long customerId, CustomerProfileRequest request);

    Customer updateProfile(Long id, CustomerProfileRequest request);

    Customer getByEmail(String email);

    void uploadDocument(Long customerId, MultipartFile file);

    List<CustomerDocument> getCustomerDocuments(Long customerId);

    CustomerDocument getDocument(Long docId);

    void deleteDocument(Long docId);

    Customer register(String email, String password);


}