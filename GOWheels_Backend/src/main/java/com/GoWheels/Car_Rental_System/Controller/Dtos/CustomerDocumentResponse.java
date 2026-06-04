package com.GoWheels.Car_Rental_System.Controller.Dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDocumentResponse implements Serializable {
    private Long id;
    private String fileName;
    private String fileType;
    private String fileUrl;
}
