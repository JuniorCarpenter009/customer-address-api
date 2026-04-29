package com.oriontek.customer_api.application.commands.customer;

import com.oriontek.customer_api.application.dto.Customer.CreateCustomerRequest;
import com.oriontek.customer_api.domain.entities.Customer;
import com.oriontek.customer_api.infrastructure.repositories.CustomerRepository;
import com.oriontek.customer_api.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UpdateCustomerCommandHandler {

    private final CustomerRepository customerRepository;

    public UpdateCustomerCommandHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer handle(UpdateCustomerCommand command) {
        Customer customer = customerRepository.findById(command.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado."));

        CreateCustomerRequest request = command.getRequest();

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setDocumentNumber(request.getDocumentNumber());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());

        return customerRepository.save(customer);
    }
}