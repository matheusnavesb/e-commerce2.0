package org.acme.dto;

import org.acme.model.Telephone;

public record TelephoneResponseDTO(
        Long id,
        String codeCountry,
        String codeState,
        String number) {

    public static TelephoneResponseDTO valueOf(Telephone telephone) {
        return new TelephoneResponseDTO(
                telephone.getId(),
                telephone.getCodeCountry(),
                telephone.getCodeState(),
                telephone.getNumber());
    }
}
