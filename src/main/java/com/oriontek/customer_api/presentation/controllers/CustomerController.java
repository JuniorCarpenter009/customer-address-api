package com.oriontek.customer_api.presentation.controllers;

import com.oriontek.customer_api.application.commands.customer.CreateCustomerCommand;
import com.oriontek.customer_api.application.commands.customer.CreateCustomerCommandHandler;
import com.oriontek.customer_api.application.commands.customer.DeleteCustomerCommand;
import com.oriontek.customer_api.application.commands.customer.DeleteCustomerCommandHandler;
import com.oriontek.customer_api.application.commands.customer.UpdateCustomerCommand;
import com.oriontek.customer_api.application.commands.customer.UpdateCustomerCommandHandler;
import com.oriontek.customer_api.application.dto.Customer.CreateCustomerRequest;
import com.oriontek.customer_api.application.queries.customer.GetAllCustomersQueryHandler;
import com.oriontek.customer_api.application.queries.customer.GetCustomerByIdQueryHandler;
import com.oriontek.customer_api.domain.entities.Customer;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CreateCustomerCommandHandler createCustomerCommandHandler;
    private final UpdateCustomerCommandHandler updateCustomerCommandHandler;
    private final DeleteCustomerCommandHandler deleteCustomerCommandHandler;
    private final GetAllCustomersQueryHandler getAllCustomersQueryHandler;
    private final GetCustomerByIdQueryHandler getCustomerByIdQueryHandler;

    public CustomerController(
            CreateCustomerCommandHandler createCustomerCommandHandler,
            UpdateCustomerCommandHandler updateCustomerCommandHandler,
            DeleteCustomerCommandHandler deleteCustomerCommandHandler,
            GetAllCustomersQueryHandler getAllCustomersQueryHandler,
            GetCustomerByIdQueryHandler getCustomerByIdQueryHandler
    ) {
        this.createCustomerCommandHandler = createCustomerCommandHandler;
        this.updateCustomerCommandHandler = updateCustomerCommandHandler;
        this.deleteCustomerCommandHandler = deleteCustomerCommandHandler;
        this.getAllCustomersQueryHandler = getAllCustomersQueryHandler;
        this.getCustomerByIdQueryHandler = getCustomerByIdQueryHandler;
    }

    @PostMapping
    public Customer create(@Valid @RequestBody CreateCustomerRequest request) {
        return createCustomerCommandHandler.handle(new CreateCustomerCommand(request));
    }

    @GetMapping
    public List<Customer> getAll() {
        return getAllCustomersQueryHandler.handle();
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable UUID id) {
        return getCustomerByIdQueryHandler.handle(id);
    }

    @PutMapping("/{id}")
    public Customer update(
            @PathVariable UUID id,
            @Valid @RequestBody CreateCustomerRequest request
    ) {
        return updateCustomerCommandHandler.handle(new UpdateCustomerCommand(id, request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        deleteCustomerCommandHandler.handle(new DeleteCustomerCommand(id));
    }
}