package org.acme.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.acme.model.User;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        String password,
        List<TelephoneResponseDTO> telephones,
        List<AddressResponseDTO> addresses) {

    public static UserResponseDTO valueOf(User user) {
        if (user == null)
            return null;

        List<TelephoneResponseDTO> telephones = user.getTelephones().stream()
                .map(TelephoneResponseDTO::valueOf)
                .collect(Collectors.toList());

        List<AddressResponseDTO> addresses = user.getAddresses().stream()
                .map(AddressResponseDTO::valueOf)
                .collect(Collectors.toList());

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                telephones,
                addresses);
    }

}
