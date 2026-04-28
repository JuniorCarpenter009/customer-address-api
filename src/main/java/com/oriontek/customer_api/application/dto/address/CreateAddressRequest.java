package com.oriontek.customer_api.application.dto.address;

import jakarta.validation.constraints.NotBlank;

public class CreateAddressRequest {

    @NotBlank(message = "La calle es obligatoria")
    private String street;

    @NotBlank(message = "La ciudad es obligatoria")
    private String city;

    @NotBlank(message = "La provincia es obligatoria")
    private String province;

    @NotBlank(message = "El país es obligatorio")
    private String country;

    private String postalCode;

    private Boolean isPrimary;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean primary) {
        isPrimary = primary;
    }
}