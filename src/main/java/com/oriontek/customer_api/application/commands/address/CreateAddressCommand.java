package com.oriontek.customer_api.application.commands.address;

import com.oriontek.customer_api.application.dto.address.CreateAddressRequest;

import java.util.UUID;

public class CreateAddressCommand {

    private final UUID customerId;
    private final CreateAddressRequest request;

    public CreateAddressCommand(UUID customerId, CreateAddressRequest request) {
        this.customerId = customerId;
        this.request = request;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public CreateAddressRequest getRequest() {
        return request;
    }
}