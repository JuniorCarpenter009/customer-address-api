package com.oriontek.customer_api.application.commands.address;

import com.oriontek.customer_api.application.dto.address.CreateAddressRequest;

import java.util.UUID;

public class UpdateAddressCommand {

    private final UUID customerId;
    private final UUID addressId;
    private final CreateAddressRequest request;

    public UpdateAddressCommand(UUID customerId, UUID addressId, CreateAddressRequest request) {
        this.customerId = customerId;
        this.addressId = addressId;
        this.request = request;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getAddressId() {
        return addressId;
    }

    public CreateAddressRequest getRequest() {
        return request;
    }
}