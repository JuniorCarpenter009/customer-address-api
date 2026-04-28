package com.oriontek.customer_api.application.services;

import com.oriontek.customer_api.application.dto.address.CreateAddressRequest;
import com.oriontek.customer_api.domain.entities.Address;
import com.oriontek.customer_api.domain.entities.Customer;
import com.oriontek.customer_api.infrastructure.repositories.AddressRepository;
import com.oriontek.customer_api.infrastructure.repositories.CustomerRepository;
import com.oriontek.customer_api.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    public AddressService(AddressRepository addressRepository, CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    public Address create(UUID customerId, CreateAddressRequest request) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado."));

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

    public List<Address> getByCustomerId(UUID customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Cliente no encontrado.");
        }

        return addressRepository.findByCustomerId(customerId);
    }

    public Address update(UUID customerId, UUID addressId, CreateAddressRequest request) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Dirección no encontrada."));

        if (!address.getCustomer().getId().equals(customerId)) {
            throw new ResourceNotFoundException("La dirección no pertenece a este cliente.");
        }

        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setProvince(request.getProvince());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());
        address.setIsPrimary(request.getIsPrimary());

        return addressRepository.save(address);
    }

    public void delete(UUID customerId, UUID addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Dirección no encontrada."));

        if (!address.getCustomer().getId().equals(customerId)) {
            throw new ResourceNotFoundException("La dirección no pertenece a este cliente.");
        }

        addressRepository.delete(address);
    }
}