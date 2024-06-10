package org.acme.service;

import java.util.List;

import org.acme.dto.AddressDTO;
import org.acme.dto.TelephoneDTO;
import org.acme.dto.UserDTO;
import org.acme.dto.UserResponseDTO;

public interface UserService {
    
    public UserResponseDTO insert(UserDTO dto);

    public UserResponseDTO update(UserDTO dto, Long id);

    public void delete(Long id);

    public UserResponseDTO findById(Long id);

    public List<UserResponseDTO> findByName(String nome);

    public List<UserResponseDTO> findByAll(); 

    public UserResponseDTO findByEmailAndPassword(String login, String senha);

    UserResponseDTO addTelephone(Long userId, TelephoneDTO telephoneRequest);
    UserResponseDTO updateTelephone(Long userId, Long telephoneId, TelephoneDTO telephoneRequest);

    UserResponseDTO addAddress(Long userId, AddressDTO addressRequest);
    UserResponseDTO updateAddress(Long userId, Long addressId, AddressDTO addressRequest);

}
