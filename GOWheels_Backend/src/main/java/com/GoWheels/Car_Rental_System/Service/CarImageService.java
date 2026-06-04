package com.GoWheels.Car_Rental_System.Service;

import com.GoWheels.Car_Rental_System.Entity.CarImage;
import org.springframework.web.multipart.MultipartFile;

public interface CarImageService {

    CarImage uploadImage(
            Long carId,
            MultipartFile file
    );

    void deleteImage(Long imageId);
}