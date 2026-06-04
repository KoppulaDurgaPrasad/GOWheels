package com.GoWheels.Car_Rental_System.Controller.Dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class CarImageResponseDto implements Serializable {

    private Long id;
    private String imageUrl;
}