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



@Service
@RequiredArgsConstructor
public class CarImageServiceImpl
        implements CarImageService {

    private final CarRepository carRepository;
    private final CloudinaryService cloudinaryService;

    private final CarImageRepository
            carImageRepository;

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

            String imageUrl =
                    cloudinaryService.uploadImage(file);

            CarImage image = new CarImage();

            image.setCar(car);

            image.setImageUrl(imageUrl);

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
            @CacheEvict(value = "car", key = "#imageId"),
            @CacheEvict(value = "cars", allEntries = true),
            @CacheEvict(value = "availableCars", allEntries = true)
    })
    public void deleteImage(Long imageId) {

        CarImage image =
                carImageRepository.findById(imageId)
                        .orElseThrow(() ->
                                new RuntimeException("Image not found"));

        carImageRepository.delete(image);
    }
}