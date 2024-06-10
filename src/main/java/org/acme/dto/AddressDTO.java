package org.acme.dto;

public record AddressDTO(
        
        String street,
        String city,
        String state,
        String zipcode,
        String country
        ) {

}
