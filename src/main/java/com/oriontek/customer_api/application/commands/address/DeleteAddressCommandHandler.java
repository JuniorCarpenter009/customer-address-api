package com.oriontek.customer_api.application.commands.address;

import com.oriontek.customer_api.domain.entities.Address;
import com.oriontek.customer_api.infrastructure.repositories.AddressRepository;
import com.oriontek.customer_api.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteAddressCommandHandler {

    private final AddressRepository addressRepository;

    public DeleteAddressCommandHandler(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void handle(DeleteAddressCommand command) {
        Address address = addressRepository.findById(command.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Dirección no encontrada."));

        if (!address.getCustomer().getId().equals(command.getCustomerId())) {
            throw new ResourceNotFoundException("La dirección no pertenece a este cliente.");
        }

        addressRepository.delete(address);
    }
}