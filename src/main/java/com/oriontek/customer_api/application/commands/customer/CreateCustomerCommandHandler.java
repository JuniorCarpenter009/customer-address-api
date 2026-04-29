package com.oriontek.customer_api.application.commands.customer;

import com.oriontek.customer_api.application.dto.Customer.CreateCustomerRequest;
import com.oriontek.customer_api.domain.entities.Customer;
import com.oriontek.customer_api.infrastructure.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCustomerCommandHandler {

    private final CustomerRepository customerRepository;

    public CreateCustomerCommandHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer handle(CreateCustomerCommand command) {
        CreateCustomerRequest request = command.getRequest();

        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Ya existe un cliente con este correo.");
        }

        if (customerRepository.existsByDocumentNumber(request.getDocumentNumber())) {
            throw new IllegalArgumentException("Ya existe un cliente con este documento.");
        }

        Customer customer = new Customer(
                request.getFirstName(),
                request.getLastName(),
                request.getDocumentNumber(),
                request.getEmail(),
                request.getPhone()
        );

        return customerRepository.save(customer);
    }
}