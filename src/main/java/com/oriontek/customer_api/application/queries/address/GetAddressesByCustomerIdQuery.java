package com.oriontek.customer_api.application.queries.address;

import java.util.UUID;

public class GetAddressesByCustomerIdQuery {

    private final UUID customerId;

    public GetAddressesByCustomerIdQuery(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}