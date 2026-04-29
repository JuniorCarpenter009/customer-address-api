package com.oriontek.customer_api.application.commands.customer;

import com.oriontek.customer_api.domain.entities.Customer;
import com.oriontek.customer_api.infrastructure.repositories.CustomerRepository;
import com.oriontek.customer_api.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteCustomerCommandHandler {

    private final CustomerRepository customerRepository;

    public DeleteCustomerCommandHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void handle(DeleteCustomerCommand command) {
        Customer customer = customerRepository.findById(command.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado."));

        customerRepository.delete(customer);
    }
}