package org.acme.dto;

import java.util.List;

public record UserDTO(
                String name,
                String email,
                String password,
                List<TelephoneDTO> telephones,
                List<AddressDTO> addresses) {

}
