package com.GoWheels.Car_Rental_System.Entity;

import com.GoWheels.Car_Rental_System.Entity.Type.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    private LocalDate dob;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer age;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = true)
    private String password;

    @Column(unique = true)
    private String contactNo;

    @Column(unique = true)
    private String licenseNumber;

    @Enumerated(EnumType.STRING)
    private IdentityProofType identityProofType;

    @Column(unique = true)
    private String identityProofNumber;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<CustomerDocument> documents = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    private boolean isProfileComplete;

    public void addDocument(CustomerDocument doc) {

        documents.add(doc);

        doc.setCustomer(this);
    }

    @PrePersist
    public void onCreate() {

        if (this.role == null) {
            this.role = RoleType.CUSTOMER;
        }

        if (this.provider == null) {
            this.provider = AuthProvider.LOCAL;
        }

        this.isProfileComplete = false;

        calculateAge();
        formatContactNumber();
    }

    @PreUpdate
    public void onUpdate() {
        calculateAge();
        formatContactNumber();
    }

    private void calculateAge() {
        if (dob != null) {
            this.age = Period.between(dob, LocalDate.now()).getYears();
        }
    }

    private void formatContactNumber() {
        if (contactNo == null || contactNo.isBlank())
            return;
        contactNo = contactNo.trim();

        if (contactNo.startsWith("+91"))
            return;

        if (contactNo.startsWith(("0")))
            contactNo = contactNo.substring(1);
        this.contactNo = "+91 " + contactNo;
    }
}