package com.oriontek.customer_api.application.queries.customer;

import com.oriontek.customer_api.domain.entities.Customer;
import com.oriontek.customer_api.infrastructure.repositories.CustomerRepository;
import com.oriontek.customer_api.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetCustomerByIdQueryHandler {

    private final CustomerRepository customerRepository;

    public GetCustomerByIdQueryHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer handle(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado."));
    }
}