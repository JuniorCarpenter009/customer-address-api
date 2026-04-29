package com.oriontek.customer_api.application.commands.customer;

import com.oriontek.customer_api.application.dto.Customer.CreateCustomerRequest;

import java.util.UUID;

public class UpdateCustomerCommand {

    private final UUID customerId;
    private final CreateCustomerRequest request;

    public UpdateCustomerCommand(UUID customerId, CreateCustomerRequest request) {
        this.customerId = customerId;
        this.request = request;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public CreateCustomerRequest getRequest() {
        return request;
    }
}