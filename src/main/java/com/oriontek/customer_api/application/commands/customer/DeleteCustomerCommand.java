package com.oriontek.customer_api.application.commands.customer;

import java.util.UUID;

public class DeleteCustomerCommand {

    private final UUID customerId;

    public DeleteCustomerCommand(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}