package com.GoWheels.Car_Rental_System.Entity;

import com.GoWheels.Car_Rental_System.Entity.Type.CarStatus;
import com.GoWheels.Car_Rental_System.Entity.Type.Category;
import com.GoWheels.Car_Rental_System.Entity.Type.FuelType;
import com.GoWheels.Car_Rental_System.Entity.Type.TransmissionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cars")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Car brand is required")
    @Column(name = "car_brand")
    private String carBrand;

    @NotBlank(message = "Car model is required")
    @Column(name = "car_model")
    private String carModel;

    @NotNull(message = "Manufactured year is required")
    @Column(name = "manufactured_year")
    private Integer manufacturedYear;

    @NotNull(message = "Price per day is required")
    @Column(name = "price_per_day")
    private BigDecimal pricePerDay;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private FuelType fuelType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transmission")
    private TransmissionType transmission;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @NotNull
    @Column(name = "seats")
    private Integer seats;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "car_status")
    private CarStatus carStatus;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<CarImage> images;


    public void addImage(CarImage image) {
        images.add(image);
        image.setCar(this);
    }

    public void removeImage(CarImage image) {
        images.remove(image);
        image.setCar(null);
    }
}