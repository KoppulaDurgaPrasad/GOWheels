package com.GoWheels.Car_Rental_System.Repository;

import com.GoWheels.Car_Rental_System.Entity.CustomerDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerDocumentRepository
        extends JpaRepository<CustomerDocument, Long> {

    List<CustomerDocument> findByCustomer_Id(Long customerId);

    void deleteByCustomer_Id(Long customerId);
}