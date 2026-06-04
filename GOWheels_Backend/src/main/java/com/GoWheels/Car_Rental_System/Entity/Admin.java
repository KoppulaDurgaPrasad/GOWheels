package com.GoWheels.Car_Rental_System.Entity;

import com.GoWheels.Car_Rental_System.Entity.Type.AuthProvider;
import com.GoWheels.Car_Rental_System.Entity.Type.GenderType;
import com.GoWheels.Car_Rental_System.Entity.Type.RoleType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "admin")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

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

    @Column(unique = true, nullable = false)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(unique = true)
    private String contactNo;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @PrePersist
    public void onCreate() {

        if (this.role == null) {
            this.role = RoleType.ADMIN;
        }

        if (this.provider == null) {
            this.provider = AuthProvider.LOCAL;
        }
        formatContactNumber();
        calculateAgeInternal();
    }

    @PreUpdate
    public void onUpdate() {
        calculateAgeInternal();
        formatContactNumber();
        ;
    }

    private void calculateAgeInternal() {
        if (dob != null) {
            this.age = Period.between(dob, LocalDate.now()).getYears();
        }
    }

    private void formatContactNumber() {
        if (contactNo != null)
            contactNo = contactNo.trim();
        if (contactNo.startsWith("+91"))
            return;
        if (contactNo.startsWith(("0")))
            contactNo = contactNo.substring(1);
        this.contactNo = "+91 " + contactNo;
    }
}
