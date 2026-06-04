package com.GoWheels.Car_Rental_System.Service;

import com.GoWheels.Car_Rental_System.Controller.Dtos.CustomerProfileRequest;
import com.GoWheels.Car_Rental_System.Entity.Customer;
import com.GoWheels.Car_Rental_System.Entity.CustomerDocument;
import com.GoWheels.Car_Rental_System.Entity.Type.AuthProvider;
import com.GoWheels.Car_Rental_System.Entity.Type.RoleType;
import com.GoWheels.Car_Rental_System.Repository.CustomerDocumentRepository;
import com.GoWheels.Car_Rental_System.Repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repo;
    private final CustomerDocumentRepository docRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Customer loginWithGoogle(String email, String providerId) {

        Customer customer = repo.findByEmail(email)
                .orElseGet(() -> {
                    Customer c = new Customer();
                    c.setEmail(email);
                    c.setProfileComplete(false);
                    return c;
                });
        if (customer.getRole() == null) {
            customer.setRole(RoleType.CUSTOMER);
        }
        if (customer.getProviderId() == null) {
            customer.setProviderId(providerId);
        }

        if (customer.getProvider() != AuthProvider.GOOGLE) {
            customer.setProvider(AuthProvider.GOOGLE);
        }
        customer.setEmail(customer.getEmail().toLowerCase().trim());
        return repo.save(customer);
    }

    @Override
    public Customer login(String email, String password) {

        Customer customer = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (customer.getPassword() == null ||
                !passwordEncoder.matches(password, customer.getPassword())) {

            throw new RuntimeException("Invalid credentials");
        }

        return customer;
    }

    @CacheEvict(value = "customers", key = "#result.email")
    @Override
    public Customer completeProfile(Long customerId, CustomerProfileRequest request) {

        Customer customer = repo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (request.getFirstName() == null || request.getFirstName().isBlank() ||
                request.getLastName() == null || request.getLastName().isBlank() ||
                request.getContactNo() == null || request.getContactNo().isBlank() ||
                request.getLicenseNumber() == null || request.getLicenseNumber().isBlank() ||
                request.getIdentityProofNumber() == null || request.getIdentityProofNumber().isBlank()) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "All fields are required");
        }
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());

        customer.setGender(request.getGender());
        customer.setDob(request.getDob());

        customer.setContactNo(request.getContactNo());

        customer.setLicenseNumber(request.getLicenseNumber());

        customer.setIdentityProofType(
                request.getIdentityProofType());

        customer.setIdentityProofNumber(
                request.getIdentityProofNumber());

        if (customer.getProvider() == AuthProvider.GOOGLE) {

            if (request.getPassword() == null || request.getPassword().isBlank()) {
                throw new RuntimeException("Password required for Google users");
            }

            if (!request.getPassword().equals(request.getConfirmPassword())) {
                throw new RuntimeException("Passwords do not match");
            }

            customer.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        customer.setProfileComplete(true);

        return repo.save(customer);
    }

    @CacheEvict(value = "customers", key = "#result.email")
    @Override
    public Customer updateProfile(Long id, CustomerProfileRequest req) {

        Customer customer = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setFirstName(req.getFirstName());
        customer.setLastName(req.getLastName());
        customer.setGender(req.getGender());
        customer.setDob(req.getDob());
        customer.setContactNo(req.getContactNo());

        customer.setLicenseNumber(req.getLicenseNumber());
        customer.setIdentityProofType(req.getIdentityProofType());
        customer.setIdentityProofNumber(req.getIdentityProofNumber());

        if (req.getPassword() != null && !req.getPassword().isBlank()) {

            if (!req.getPassword().equals(req.getConfirmPassword())) {
                throw new RuntimeException("Passwords do not match");
            }

            customer.setPassword(passwordEncoder.encode(req.getPassword()));
        }

        return repo.save(customer);
    }

    @Transactional
    @Override
    @CacheEvict(value = "customers", allEntries = true)
    public void uploadDocument(Long customerId,
                               MultipartFile file) {

        if (file == null || file.isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "File is required"
            );
        }

        try {

            Customer customer = repo.findById(customerId)
                    .orElseThrow(() ->
                            new RuntimeException("Customer not found"));

            docRepo.deleteByCustomer_Id(customerId);
            CustomerDocument doc =
                    new CustomerDocument();

            doc.setFileName(
                    file.getOriginalFilename());

            doc.setFileType(
                    file.getContentType());

            doc.setData(
                    file.getBytes());

            customer.addDocument(doc);

            repo.save(customer);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Upload failed"
            );
        }
    }

    @Override
    public List<CustomerDocument> getCustomerDocuments(Long id) {

        return docRepo.findByCustomer_Id(id);
    }

    @Override
    public CustomerDocument getDocument(Long id) {
        return docRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
    }

    @Override
    @CacheEvict(value = "customers", allEntries = true)
    public void deleteDocument(Long id) {

        CustomerDocument doc = docRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Document not found"));

        docRepo.delete(doc);
    }

    @Transactional
    @Override
    public Customer getByEmail(String email) {

        return repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public Customer register(String email, String password) {

        if (repo.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        Customer c = new Customer();
        c.setEmail(email);
        c.setPassword(passwordEncoder.encode(password));
        c.setProfileComplete(false);
        c.setRole(RoleType.CUSTOMER);

        return repo.save(c);
    }
}