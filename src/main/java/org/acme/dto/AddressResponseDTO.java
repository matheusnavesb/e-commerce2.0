package org.acme.dto;

import org.acme.model.Address;

public record AddressResponseDTO(
        Long id,
        String street,
        String city,
        String state,
        String zipcode,
        String country) {

    public static AddressResponseDTO valueOf(Address address) {

        return new AddressResponseDTO(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZipcode(),
                address.getCountry());

    }

}
