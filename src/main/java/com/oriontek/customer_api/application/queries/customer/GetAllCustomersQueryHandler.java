package com.oriontek.customer_api.application.queries.customer;

import com.oriontek.customer_api.domain.entities.Customer;
import com.oriontek.customer_api.infrastructure.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCustomersQueryHandler {

    private final CustomerRepository customerRepository;

    public GetAllCustomersQueryHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> handle() {
        return customerRepository.findAll();
    }
}