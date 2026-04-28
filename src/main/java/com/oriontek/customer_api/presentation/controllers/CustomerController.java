package com.oriontek.customer_api.presentation.controllers;

import com.oriontek.customer_api.application.dto.Customer.CreateCustomerRequest;
import com.oriontek.customer_api.application.services.CustomerService;
import com.oriontek.customer_api.domain.entities.Customer;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer create(@Valid @RequestBody CreateCustomerRequest request) {
        return customerService.create(request);
    }

    @GetMapping
    public List<Customer> getAll() {
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable UUID id) {
        return customerService.getById(id);
    }

    @PutMapping("/{id}")
    public Customer update(
            @PathVariable UUID id,
            @Valid @RequestBody CreateCustomerRequest request
    ) {
        return customerService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        customerService.delete(id);
    }
}