package com.GoWheels.Car_Rental_System.Service;

import com.GoWheels.Car_Rental_System.Entity.Car;
import com.GoWheels.Car_Rental_System.Entity.CarImage;
import com.GoWheels.Car_Rental_System.Repository.CarImageRepository;
import com.GoWheels.Car_Rental_System.Repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class CarImageServiceImpl
        implements CarImageService {

    private final CarRepository carRepository;

    private final CarImageRepository
            carImageRepository;

     @Value("${APP_URL}")
     private String appUrl;

    @Override
    @CacheEvict(
            value = {"cars", "availableCars"},
            allEntries = true
    )
    public CarImage uploadImage(

            Long carId,

            MultipartFile file
    ) {

        try {

            Car car = carRepository
                    .findById(carId)

                    .orElseThrow(() ->

                            new RuntimeException(
                                    "Car not found"
                            )
                    );
            if (car.getImages() != null
                    &&
                    car.getImages().size() >= 4) {

                throw new RuntimeException(
                        "Maximum 4 images allowed"
                );
            }
            String uploadDir = "uploads/";

            File dir = new File(uploadDir);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName =

                    System.currentTimeMillis()
                            + "_"
                            + file.getOriginalFilename();

            Path path = Paths.get(
                    uploadDir + fileName
            );

            Files.write(
                    path,
                    file.getBytes()
            );

            CarImage image = new CarImage();

            image.setCar(car);

            image.setImageUrl(
             appUrl + "/uploads/" + fileName
            );

            return carImageRepository
                    .save(image);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Image upload failed"
            );
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "car", key = "#carId"),
            @CacheEvict(value = "cars", allEntries = true),
            @CacheEvict(value = "availableCars", allEntries = true)
    })
    public void deleteImage(Long imageId) {

        CarImage image =

                carImageRepository
                        .findById(imageId)

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Image not found"
                                )
                        );

        try {
            String imageUrl =
                    image.getImageUrl();

            String fileName =
                    imageUrl.substring(
                            imageUrl.lastIndexOf("/") + 1
                    );

            Path path = Paths.get(
                    "uploads/" + fileName
            );

            Files.deleteIfExists(path);

        } catch (Exception e) {

            e.printStackTrace();
        }
        carImageRepository.delete(image);
    }
}
