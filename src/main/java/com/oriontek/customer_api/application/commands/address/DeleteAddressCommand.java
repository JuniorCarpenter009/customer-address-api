package com.oriontek.customer_api.application.commands.address;

import java.util.UUID;

public class DeleteAddressCommand {

    private final UUID customerId;
    private final UUID addressId;

    public DeleteAddressCommand(UUID customerId, UUID addressId) {
        this.customerId = customerId;
        this.addressId = addressId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getAddressId() {
        return addressId;
    }
}