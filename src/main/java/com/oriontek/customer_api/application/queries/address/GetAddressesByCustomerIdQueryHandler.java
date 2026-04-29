package com.oriontek.customer_api.application.queries.address;

import com.oriontek.customer_api.domain.entities.Address;
import com.oriontek.customer_api.infrastructure.repositories.AddressRepository;
import com.oriontek.customer_api.infrastructure.repositories.CustomerRepository;
import com.oriontek.customer_api.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAddressesByCustomerIdQueryHandler {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    public GetAddressesByCustomerIdQueryHandler(
            AddressRepository addressRepository,
            CustomerRepository customerRepository
    ) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    public List<Address> handle(GetAddressesByCustomerIdQuery query) {
        if (!customerRepository.existsById(query.getCustomerId())) {
            throw new ResourceNotFoundException("Cliente no encontrado.");
        }

        return addressRepository.findByCustomerId(query.getCustomerId());
    }
}