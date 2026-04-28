package com.oriontek.customer_api.infrastructure.repositories;

import com.oriontek.customer_api.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    boolean existsByEmail(String email);

    boolean existsByDocumentNumber(String documentNumber);
}