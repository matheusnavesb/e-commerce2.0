package org.acme.service;

import org.acme.dto.UserResponseDTO;

public interface JwtService {
    
    public String generateJwt(UserResponseDTO dto);


}
