package com.GoWheels.Car_Rental_System.Service;


import com.GoWheels.Car_Rental_System.Repository.AdminRepository;
import com.GoWheels.Car_Rental_System.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final CustomerRepository customerRepo;
    private final AdminRepository adminRepo;
    private final PasswordEncoder passwordEncoder;

    public Object login(String email, String password) {

        var customerOpt = customerRepo.findByEmail(email);

        if (customerOpt.isPresent()) {
            var customer = customerOpt.get();

            if (passwordEncoder.matches(password, customer.getPassword())) {
                return customer;
            }
        }

        var adminOpt = adminRepo.findByEmail(email);

        if (adminOpt.isPresent()) {
            var admin = adminOpt.get();

            if (passwordEncoder.matches(password, admin.getPassword())) {
                return admin;
            }
        }

        throw new RuntimeException("Invalid credentials");
    }
}
