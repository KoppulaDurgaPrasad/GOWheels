package com.GoWheels.Car_Rental_System.Service;


import com.GoWheels.Car_Rental_System.Controller.Dtos.AdminProfileRequest;
import com.GoWheels.Car_Rental_System.Controller.Dtos.CustomerDetailsDto;
import com.GoWheels.Car_Rental_System.Controller.Dtos.CustomerDocumentResponse;
import com.GoWheels.Car_Rental_System.Entity.Admin;
import com.GoWheels.Car_Rental_System.Entity.Customer;
import com.GoWheels.Car_Rental_System.Entity.Rental;
import com.GoWheels.Car_Rental_System.Entity.Type.AuthProvider;
import com.GoWheels.Car_Rental_System.Entity.Type.RoleType;
import com.GoWheels.Car_Rental_System.Repository.AdminRepository;
import com.GoWheels.Car_Rental_System.Repository.CustomerDocumentRepository;
import com.GoWheels.Car_Rental_System.Repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final RentalRepository rentalRepository;
    private final CustomerDocumentRepository customerDocumentRepository;

    @Override
    public Admin loginWithGoogle(String email, String providerId) {

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Unauthorized: Admin not found"));

        if (admin.getProvider() != AuthProvider.GOOGLE) {
            admin.setProvider(AuthProvider.GOOGLE);
        }

        if (admin.getProviderId() == null) {
            admin.setProviderId(providerId);
        }

        admin.setEmail(email.toLowerCase().trim());

        if (admin.getRole() == null) {
            admin.setRole(RoleType.ADMIN);
        }

        return adminRepository.save(admin);
    }

    @Override
    public Admin login(String email, String password) {

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (admin.getPassword() == null ||
                !passwordEncoder.matches(password, admin.getPassword())) {

            throw new RuntimeException("Invalid credentials");
        }

        return admin;
    }

    @CacheEvict(value = "admins", key = "#result.email")
    @Override
    public Admin updateProfile(Long adminId, AdminProfileRequest request) {

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        admin.setFirstName(request.getFirstName());
        admin.setLastName(request.getLastName());
        admin.setGender(request.getGender());
        admin.setDob(request.getDob());
        admin.setContactNo(request.getContactNo());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {

            if (!request.getPassword().equals(request.getConfirmPassword())) {
                throw new RuntimeException("Passwords do not match");
            }

            admin.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return adminRepository.save(admin);
    }


    @Override
    public Admin getByEmail(String email) {
        return adminRepository.findByEmail(email.toLowerCase().trim()) // ✅ FIX
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    @Override
    public Admin save(Admin admin) {

        if (admin.getRole() == null) {
            admin.setRole(RoleType.ADMIN);
        }

        if (admin.getProvider() == null) {
            admin.setProvider(AuthProvider.LOCAL);
        }

        if (admin.getPassword() == null || admin.getPassword().isBlank()) {
            throw new RuntimeException("Password is required");
        }

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setEmail(admin.getEmail().toLowerCase().trim());

        return adminRepository.save(admin);
    }

    @Override
    public CustomerDetailsDto getCustomerDetails(Long rentalId) {

        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        Customer customer = rental.getCustomer();

        List<CustomerDocumentResponse> docs =
                customerDocumentRepository.findByCustomer_Id(customer.getId())
                        .stream()
                        .map(d -> new CustomerDocumentResponse(
                                d.getId(),
                                d.getFileName(),
                                d.getFileType(),
                                "/api/customer/view/" + d.getId()
                        ))
                        .toList();

        return new CustomerDetailsDto(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getContactNo(),
                customer.getLicenseNumber(),
                customer.getIdentityProofType(),
                customer.getIdentityProofNumber(),
                docs
        );
    }
}