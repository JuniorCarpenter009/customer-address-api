package com.oriontek.customer_api.presentation.controllers;

import com.oriontek.customer_api.application.dto.address.CreateAddressRequest;
import com.oriontek.customer_api.application.services.AddressService;
import com.oriontek.customer_api.domain.entities.Address;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers/{customerId}/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public Address create(
            @PathVariable UUID customerId,
            @Valid @RequestBody CreateAddressRequest request
    ) {
        return addressService.create(customerId, request);
    }

    @GetMapping
    public List<Address> getByCustomerId(@PathVariable UUID customerId) {
        return addressService.getByCustomerId(customerId);
    }

   @PutMapping("/{addressId}")
public Address update(
        @PathVariable UUID customerId,
        @PathVariable UUID addressId,
        @Valid @RequestBody CreateAddressRequest request
) {
    return addressService.update(customerId, addressId, request);
}

    @DeleteMapping("/{addressId}")
    public void delete(
        @PathVariable UUID customerId,
        @PathVariable UUID addressId
)  {
    addressService.delete(customerId, addressId);
}
}