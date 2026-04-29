package com.oriontek.customer_api.application.commands.customer;

import com.oriontek.customer_api.application.dto.Customer.CreateCustomerRequest;

public class CreateCustomerCommand {

    private final CreateCustomerRequest request;

    public CreateCustomerCommand(CreateCustomerRequest request) {
        this.request = request;
    }

    public CreateCustomerRequest getRequest() {
        return request;
    }
}