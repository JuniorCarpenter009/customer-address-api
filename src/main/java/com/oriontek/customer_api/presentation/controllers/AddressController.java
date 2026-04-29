package com.oriontek.customer_api.presentation.controllers;

import com.oriontek.customer_api.application.commands.address.CreateAddressCommand;
import com.oriontek.customer_api.application.commands.address.CreateAddressCommandHandler;
import com.oriontek.customer_api.application.commands.address.DeleteAddressCommand;
import com.oriontek.customer_api.application.commands.address.DeleteAddressCommandHandler;
import com.oriontek.customer_api.application.commands.address.UpdateAddressCommand;
import com.oriontek.customer_api.application.commands.address.UpdateAddressCommandHandler;
import com.oriontek.customer_api.application.dto.address.CreateAddressRequest;
import com.oriontek.customer_api.application.queries.address.GetAddressesByCustomerIdQuery;
import com.oriontek.customer_api.application.queries.address.GetAddressesByCustomerIdQueryHandler;
import com.oriontek.customer_api.domain.entities.Address;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers/{customerId}/addresses")
public class AddressController {

    private final CreateAddressCommandHandler createAddressCommandHandler;
    private final UpdateAddressCommandHandler updateAddressCommandHandler;
    private final DeleteAddressCommandHandler deleteAddressCommandHandler;
    private final GetAddressesByCustomerIdQueryHandler getAddressesByCustomerIdQueryHandler;

    public AddressController(
            CreateAddressCommandHandler createAddressCommandHandler,
            UpdateAddressCommandHandler updateAddressCommandHandler,
            DeleteAddressCommandHandler deleteAddressCommandHandler,
            GetAddressesByCustomerIdQueryHandler getAddressesByCustomerIdQueryHandler
    ) {
        this.createAddressCommandHandler = createAddressCommandHandler;
        this.updateAddressCommandHandler = updateAddressCommandHandler;
        this.deleteAddressCommandHandler = deleteAddressCommandHandler;
        this.getAddressesByCustomerIdQueryHandler = getAddressesByCustomerIdQueryHandler;
    }

    @PostMapping
    public Address create(
            @PathVariable UUID customerId,
            @Valid @RequestBody CreateAddressRequest request
    ) {
        return createAddressCommandHandler.handle(new CreateAddressCommand(customerId, request));
    }

    @GetMapping
    public List<Address> getByCustomerId(@PathVariable UUID customerId) {
        return getAddressesByCustomerIdQueryHandler.handle(new GetAddressesByCustomerIdQuery(customerId));
    }

    @PutMapping("/{addressId}")
    public Address update(
            @PathVariable UUID customerId,
            @PathVariable UUID addressId,
            @Valid @RequestBody CreateAddressRequest request
    ) {
        return updateAddressCommandHandler.handle(new UpdateAddressCommand(customerId, addressId, request));
    }

    @DeleteMapping("/{addressId}")
    public void delete(
            @PathVariable UUID customerId,
            @PathVariable UUID addressId
    ) {
        deleteAddressCommandHandler.handle(new DeleteAddressCommand(customerId, addressId));
    }
}