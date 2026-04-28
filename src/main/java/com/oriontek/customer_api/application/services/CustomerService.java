package com.oriontek.customer_api.application.services;

import com.oriontek.customer_api.application.dto.Customer.CreateCustomerRequest;
import com.oriontek.customer_api.domain.entities.Customer;
import com.oriontek.customer_api.infrastructure.repositories.CustomerRepository;
import com.oriontek.customer_api.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(CreateCustomerRequest request) {

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

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getById(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado."));
    }

    public Customer update(UUID id, CreateCustomerRequest request) {
        Customer customer = getById(id);

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setDocumentNumber(request.getDocumentNumber());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());

        return customerRepository.save(customer);
    }

    public void delete(UUID id) {
        Customer customer = getById(id);
        customerRepository.delete(customer);
    }
}