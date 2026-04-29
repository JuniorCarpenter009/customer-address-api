package com.oriontek.customer_api.application.commands.address;

import com.oriontek.customer_api.application.dto.address.CreateAddressRequest;
import com.oriontek.customer_api.domain.entities.Address;
import com.oriontek.customer_api.infrastructure.repositories.AddressRepository;
import com.oriontek.customer_api.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UpdateAddressCommandHandler {

    private final AddressRepository addressRepository;

    public UpdateAddressCommandHandler(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address handle(UpdateAddressCommand command) {
        Address address = addressRepository.findById(command.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Dirección no encontrada."));

        if (!address.getCustomer().getId().equals(command.getCustomerId())) {
            throw new ResourceNotFoundException("La dirección no pertenece a este cliente.");
        }

        CreateAddressRequest request = command.getRequest();

        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setProvince(request.getProvince());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());
        address.setIsPrimary(request.getIsPrimary());

        return addressRepository.save(address);
    }
}