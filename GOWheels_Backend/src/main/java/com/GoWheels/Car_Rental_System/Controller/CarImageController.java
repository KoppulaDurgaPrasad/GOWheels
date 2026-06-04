package com.GoWheels.Car_Rental_System.Controller;

import com.GoWheels.Car_Rental_System.Entity.CarImage;
import com.GoWheels.Car_Rental_System.Service.CarImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/images")
@RequiredArgsConstructor
public class CarImageController {

    private final CarImageService carImageService;

    @PostMapping("/upload/{carId}")
    public ResponseEntity<CarImage> uploadImage(

            @PathVariable Long carId,

            @RequestParam("file")
            MultipartFile file
    ) {

        return ResponseEntity.ok(

                carImageService.uploadImage(
                        carId,
                        file
                )
        );
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<String> deleteImage(

            @PathVariable Long imageId
    ) {

        carImageService.deleteImage(imageId);

        return ResponseEntity.ok(
                "Image deleted successfully"
        );
    }
}