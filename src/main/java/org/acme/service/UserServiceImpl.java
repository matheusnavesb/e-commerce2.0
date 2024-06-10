package org.acme.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.acme.dto.AddressDTO;
import org.acme.dto.TelephoneDTO;
import org.acme.dto.UserDTO;
import org.acme.dto.UserResponseDTO;
import org.acme.model.Address;
import org.acme.model.Telephone;
import org.acme.model.User;
import org.acme.repository.UserRepository;
import org.acme.validation.ValidationException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository repository;

    @Inject
    Validator validator;

    @Override
    @Transactional
    public UserResponseDTO insert(UserDTO dto) {
        if (repository.findByEmail(dto.email()).firstResult() != null) {
            throw new ValidationException("email", "O email informado já existe, informe outro email.");
        }
        User novoUser = new User();
        novoUser.setName(dto.name());
        novoUser.setEmail(dto.email());
        novoUser.setPassword(dto.password());

        // Telefones e endereços são opcionais no insert
        if (dto.telephones() != null) {
            List<Telephone> telephones = dto.telephones().stream()
                    .map(t -> new Telephone(t.codeCountry(), t.codeState(), t.number(), novoUser))
                    .collect(Collectors.toList());
            novoUser.setTelephones(telephones);
        }

        if (dto.addresses() != null) {
            List<Address> addresses = dto.addresses().stream()
                    .map(a -> new Address(a.street(), a.city(), a.state(), a.zipcode(), a.country(), novoUser))
                    .collect(Collectors.toList());
            novoUser.setAddresses(addresses);
        }

        repository.persist(novoUser);

        return UserResponseDTO.valueOf(novoUser);
    }

    @Override
    @Transactional
    public UserResponseDTO update(UserDTO dto, Long id) {
        Optional<User> userOptional = repository.findByIdOptional(id);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());

        // Atualiza telefones se existirem
        if (dto.telephones() != null) {
            user.getTelephones().clear();
            List<Telephone> telephones = dto.telephones().stream()
                    .map(t -> new Telephone(t.codeCountry(), t.codeState(), t.number(), user))
                    .collect(Collectors.toList());
            user.getTelephones().addAll(telephones);
        } else {
            user.getTelephones().clear();
        }

        // Atualiza endereços se existirem
        if (dto.addresses() != null) {
            user.getAddresses().clear();
            List<Address> addresses = dto.addresses().stream()
                    .map(a -> new Address(a.street(), a.city(), a.state(), a.zipcode(), a.country(), user))
                    .collect(Collectors.toList());
            user.getAddresses().addAll(addresses);
        } else {
            user.getAddresses().clear();
        }

        repository.persist(user);
        return UserResponseDTO.valueOf(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public UserResponseDTO findById(Long id) {
        Optional<User> userOptional = repository.findByIdOptional(id);
        return userOptional.map(UserResponseDTO::valueOf).orElse(null);
    }

    @Override
    public List<UserResponseDTO> findByName(String name) {
        List<User> users = repository.findByName(name);
        return users.stream().map(UserResponseDTO::valueOf).collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDTO> findByAll() {
        List<User> users = repository.listAll();
        return users.stream().map(UserResponseDTO::valueOf).collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO findByEmailAndPassword(String email, String password) {
        User user = repository.findByLoginAndSenha(email, password);
        return UserResponseDTO.valueOf(user);
    }

    @Override
    @Transactional
    public UserResponseDTO addTelephone(Long userId, TelephoneDTO telephoneRequest) {
        Optional<User> userOptional = repository.findByIdOptional(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        Telephone newTelephone = new Telephone(telephoneRequest.codeCountry(), telephoneRequest.codeState(),
                telephoneRequest.number(), user);
        user.getTelephones().add(newTelephone);

        repository.persist(user);
        return UserResponseDTO.valueOf(user);
    }

    @Override
    @Transactional
    public UserResponseDTO updateTelephone(Long userId, Long telephoneId, TelephoneDTO telephoneRequest) {
        Optional<User> userOptional = repository.findByIdOptional(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        Telephone telephone = user.getTelephones().stream().filter(t -> t.getId().equals(telephoneId)).findFirst()
                .orElseThrow(() -> new RuntimeException("Telephone not found"));

        telephone.setCodeCountry(telephoneRequest.codeCountry());
        telephone.setCodeState(telephoneRequest.codeState());
        telephone.setNumber(telephoneRequest.number());

        repository.persist(user);
        return UserResponseDTO.valueOf(user);
    }

    @Override
    @Transactional
    public UserResponseDTO addAddress(Long userId, AddressDTO addressRequest) {
        Optional<User> userOptional = repository.findByIdOptional(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        Address newAddress = new Address(addressRequest.street(), addressRequest.city(), addressRequest.state(),
                addressRequest.zipcode(), addressRequest.country(), user);
        user.getAddresses().add(newAddress);

        repository.persist(user);
        return UserResponseDTO.valueOf(user);
    }

    @Override
    @Transactional
    public UserResponseDTO updateAddress(Long userId, Long addressId, AddressDTO addressRequest) {
        Optional<User> userOptional = repository.findByIdOptional(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        Address address = user.getAddresses().stream().filter(a -> a.getId().equals(addressId)).findFirst()
                .orElseThrow(() -> new RuntimeException("Address not found"));

        address.setStreet(addressRequest.street());
        address.setCity(addressRequest.city());
        address.setState(addressRequest.state());
        address.setZipcode(addressRequest.zipcode());
        address.setCountry(addressRequest.country());

        repository.persist(user);
        return UserResponseDTO.valueOf(user);
    }

}
