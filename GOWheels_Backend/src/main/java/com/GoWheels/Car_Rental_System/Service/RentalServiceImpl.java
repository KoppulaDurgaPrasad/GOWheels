package com.GoWheels.Car_Rental_System.Service;

import com.GoWheels.Car_Rental_System.Controller.Dtos.RentalRequestDto;
import com.GoWheels.Car_Rental_System.Controller.Dtos.RentalResponseDto;
import com.GoWheels.Car_Rental_System.Entity.Car;
import com.GoWheels.Car_Rental_System.Entity.Customer;
import com.GoWheels.Car_Rental_System.Entity.Rental;
import com.GoWheels.Car_Rental_System.Entity.Type.CarStatus;
import com.GoWheels.Car_Rental_System.Entity.Type.RentalStatus;
import com.GoWheels.Car_Rental_System.Repository.CarRepository;
import com.GoWheels.Car_Rental_System.Repository.CustomerRepository;
import com.GoWheels.Car_Rental_System.Repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;

    @Override
    public RentalResponseDto rentCar(
            Long customerId,
            RentalRequestDto request
    ) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Car car = carRepository.findById(request.getCarId())
                .orElseThrow(() ->
                        new RuntimeException("Car not found"));

        if (car.getCarStatus() != CarStatus.AVAILABLE) {
            throw new RuntimeException("Car is not available");
        }

        List<Rental> customerRentals =
                rentalRepository.findByCustomer(customer);


        LocalDate today =
                LocalDate.now();

        long totalBookings =
                customerRentals.stream()

                        .filter(rental ->

                                rental.getEndDate()
                                        .plusDays(1)
                                        .isAfter(today)
                        )

                        .count();


        if (totalBookings >= 2) {

            boolean blocked =
                    customerRentals.stream()

                            .anyMatch(rental ->

                                    rental.getEndDate()
                                            .plusDays(1)
                                            .isAfter(today)
                            );

            if (blocked) {

                throw new RuntimeException(
                        "You can book again only after end date + 1 day"
                );
            }
        }


        long days =
                ChronoUnit.DAYS.between(
                        request.getStartDate(),
                        request.getEndDate()
                ) + 1;

        BigDecimal totalAmount =
                car.getPricePerDay()
                        .multiply(BigDecimal.valueOf(days));

        Rental rental = new Rental();

        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setStartDate(request.getStartDate());
        rental.setEndDate(request.getEndDate());
        rental.setStatus(RentalStatus.PENDING);
        car.setCarStatus(CarStatus.RENTED);
        rental.setTotalAmount(totalAmount);

        Rental savedRental = rentalRepository.save(rental);

        return mapToDto(savedRental);
    }

    @Override
    public RentalResponseDto updateRentalStatus(
            Long rentalId,
            RentalStatus status
    ) {

        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() ->
                        new RuntimeException("Rental not found"));

        rental.setStatus(status);

        if (status == RentalStatus.APPROVED ||
                status == RentalStatus.ACTIVE) {

            rental.getCar()
                    .setCarStatus(CarStatus.RENTED);
        }

        if (status == RentalStatus.COMPLETED ||
                status == RentalStatus.CANCELLED) {

            rental.getCar()
                    .setCarStatus(CarStatus.AVAILABLE);
        }

        Rental updatedRental = rentalRepository.save(rental);

        return mapToDto(updatedRental);
    }

    @Override
    public List<RentalResponseDto> getCustomerRentals(
            Long customerId
    ) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        return rentalRepository.findByCustomer(customer)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalResponseDto> getAllRentals() {

        return rentalRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalResponseDto> getRentalsByStatus(
            RentalStatus status
    ) {

        return rentalRepository.findByStatus(status)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private RentalResponseDto mapToDto(Rental rental) {

        RentalResponseDto dto =
                new RentalResponseDto();

        dto.setRentalId(
                rental.getId()
        );

        dto.setStartDate(
                rental.getStartDate()
        );

        dto.setEndDate(
                rental.getEndDate()
        );

        dto.setTotalAmount(
                rental.getTotalAmount()
        );

        dto.setStatus(
                rental.getStatus()
        );
        dto.setCustomerId(
                rental.getCustomer().getId()
        );

        dto.setCustomerFirstName(
                rental.getCustomer()
                        .getFirstName()
        );

        dto.setCustomerLastName(
                rental.getCustomer()
                        .getLastName()
        );

        dto.setCustomerEmail(
                rental.getCustomer()
                        .getEmail()
        );

        dto.setCustomerPhone(
                rental.getCustomer()
                        .getContactNo()
        );
        if (rental.getCustomer().getGender() != null) {

            dto.setGender(
                    rental.getCustomer()
                            .getGender()
                            .name()
            );
        }
        dto.setAge(
                rental.getCustomer()
                        .getAge()
        );
        if (rental.getCustomer().getDob() != null) {

            dto.setDob(
                    rental.getCustomer()
                            .getDob()
                            .toString()
            );
        }
        if (rental.getCustomer().getRole() != null) {

            dto.setRole(
                    rental.getCustomer()
                            .getRole()
                            .name()
            );
        }
        dto.setLicenseNumber(
                rental.getCustomer()
                        .getLicenseNumber()
        );

        if (rental.getCustomer().getIdentityProofType() != null) {

            dto.setIdentityProofType(
                    rental.getCustomer()
                            .getIdentityProofType()
                            .name()
            );
        }
        dto.setIdentityProofNumber(
                rental.getCustomer()
                        .getIdentityProofNumber()
        );
        if (
                rental.getCustomer()
                        .getDocuments() != null
                        &&
                        !rental.getCustomer()
                                .getDocuments()
                                .isEmpty()
        ) {

            dto.setDocumentUrl(
                    "http://localhost:8080/api/customer/view/" +
                            rental.getCustomer()
                                    .getDocuments()
                                    .get(0)
                                    .getId()
            );
        }

        dto.setCarId(
                rental.getCar().getId()
        );

        dto.setCarBrand(
                rental.getCar()
                        .getCarBrand()
        );

        dto.setCarModel(
                rental.getCar()
                        .getCarModel()
        );

        if (
                rental.getCar().getImages() != null
                        &&
                        !rental.getCar()
                                .getImages()
                                .isEmpty()
        ) {

            dto.setImageUrl(

                    rental.getCar()
                            .getImages()
                            .get(0)
                            .getImageUrl()
            );
        }

        return dto;
    }
}
