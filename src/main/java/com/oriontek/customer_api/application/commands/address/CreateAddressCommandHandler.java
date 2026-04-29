package com.oriontek.customer_api.application.commands.address;

import com.oriontek.customer_api.application.dto.address.CreateAddressRequest;
import com.oriontek.customer_api.domain.entities.Address;
import com.oriontek.customer_api.domain.entities.Customer;
import com.oriontek.customer_api.infrastructure.repositories.AddressRepository;
import com.oriontek.customer_api.infrastructure.repositories.CustomerRepository;
import com.oriontek.customer_api.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CreateAddressCommandHandler {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    public CreateAddressCommandHandler(
            AddressRepository addressRepository,
            CustomerRepository customerRepository
    ) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    public Address handle(CreateAddressCommand command) {
        Customer customer = customerRepository.findById(command.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado."));

        CreateAddressRequest request = command.getRequest();

        Address address = new Address(
                request.getStreet(),
                request.getCity(),
                request.getProvince(),
                request.getCountry(),
                request.getPostalCode(),
                request.getIsPrimary(),
                customer
        );

        return addressRepository.save(address);
    }
}