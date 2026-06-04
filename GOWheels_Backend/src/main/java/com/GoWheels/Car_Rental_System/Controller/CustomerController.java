package com.GoWheels.Car_Rental_System.Controller;

import com.GoWheels.Car_Rental_System.Config.JwtUtil;
import com.GoWheels.Car_Rental_System.Controller.Dtos.*;
import com.GoWheels.Car_Rental_System.Entity.Customer;
import com.GoWheels.Car_Rental_System.Entity.CustomerDocument;
import com.GoWheels.Car_Rental_System.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;
    private final JwtUtil jwtUtil;

    private String getEmail() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    private CustomerProfileResponse buildResponse(Customer c) {


        List<CustomerDocumentResponse> docs =
                service.getCustomerDocuments(c.getId())
                        .stream()
                        .map(d -> new CustomerDocumentResponse(
                                d.getId(),
                                d.getFileName(),
                                d.getFileType(),
                                "/api/customer/view/" + d.getId()
                        ))
                        .toList();


        return new CustomerProfileResponse(
                c.getId(),
                c.getFirstName(),
                c.getLastName(),
                c.getEmail(),
                c.getContactNo(),
                c.getGender(),
                c.getDob(),
                c.getAge(),
                c.getLicenseNumber(),
                c.getIdentityProofType(),
                c.getIdentityProofNumber(),
                c.isProfileComplete(),
                docs,
                c.getProvider()
        );
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody LoginRequest request) {

        Customer c = service.register(
                request.getEmail(),
                request.getPassword()
        );

        String token = jwtUtil.generateToken(
                c.getEmail(),
                c.getRole().name()
        );

        return ResponseEntity.ok(
                new AuthResponse(
                        c.getId(),
                        c.getEmail(),
                        c.getRole().name(),
                        token
                )
        );
    }

    @GetMapping("/profile")
    public ResponseEntity<CustomerProfileResponse> getProfile() {
        Customer user = service.getByEmail(getEmail());
        return ResponseEntity.ok(buildResponse(user));
    }

    @GetMapping("/profile-status")
    public ResponseEntity<Boolean> getProfileStatus() {

        Customer user = service.getByEmail(getEmail());

        return ResponseEntity.ok(
                user.isProfileComplete()
        );
    }

    @PutMapping("/complete-profile")
    public ResponseEntity<CustomerProfileResponse> completeProfile(
            @RequestBody CustomerProfileRequest req) {

        Customer user = service.getByEmail(getEmail());
        Customer c = service.completeProfile(user.getId(), req);

        return ResponseEntity.ok(buildResponse(c));
    }

    @PutMapping("/update-profile")
    public ResponseEntity<CustomerProfileResponse> updateProfile(
            @RequestBody CustomerProfileRequest req) {

        Customer user = service.getByEmail(getEmail());
        Customer c = service.updateProfile(user.getId(), req);

        return ResponseEntity.ok(buildResponse(c));
    }


    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file) {

        Customer user = service.getByEmail(getEmail());
        service.uploadDocument(user.getId(), file);

        return ResponseEntity.ok("Document uploaded successfully");
    }

    @GetMapping("/documents")
    public ResponseEntity<List<CustomerDocumentResponse>> docs() {

        Customer user = service.getByEmail(getEmail());

        List<CustomerDocumentResponse> list = service.getCustomerDocuments(user.getId())
                .stream()
                .map(d -> new CustomerDocumentResponse(
                        d.getId(),
                        d.getFileName(),
                        d.getFileType(),
                        "/api/customer/view/" + d.getId()))
                .toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<byte[]> viewDocument(
            @PathVariable Long id) {

        CustomerDocument doc = service.getDocument(id);

        return ResponseEntity.ok()
                .header(
                        "Content-Disposition",
                        "inline; filename=\"" + doc.getFileName() + "\""
                )
                .header(
                        "Content-Type",
                        doc.getFileType()
                )
                .body(doc.getData());
    }

    @DeleteMapping("/document/{id}")
    public ResponseEntity<String> deleteDocument(
            @PathVariable Long id) {

        service.deleteDocument(id);

        return ResponseEntity.ok("Document deleted successfully");
    }

}