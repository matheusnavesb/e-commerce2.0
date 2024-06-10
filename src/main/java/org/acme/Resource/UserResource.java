package org.acme.Resource;

import java.util.List;

import org.acme.dto.AddressDTO;
import org.acme.dto.TelephoneDTO;
import org.acme.dto.UserDTO;
import org.acme.dto.UserResponseDTO;
import org.acme.service.UserService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    
    @Inject
    UserService service;

    @POST
    @Transactional
    public UserResponseDTO insert(UserDTO dto) {
        return service.insert(dto);
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public UserResponseDTO update(UserDTO dto, @PathParam("id") Long id) {
        return service.update(dto, id);
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        service.delete(id);
    }

    @GET
    public List<UserResponseDTO> findAll() {
        return service.findByAll();
    }

    @GET
    @Path("/{id}")
    public UserResponseDTO findById(@PathParam("id") Long id) {
        return service.findById(id);
    }
    
    @GET
    @Path("/search/nome/{nome}")
    public List<UserResponseDTO> findByNome(@PathParam("nome") String nome) {
        return service.findByName(nome);
    }
    
    @POST
    @Transactional
    @Path("/{id}/telephones")
    public UserResponseDTO addTelephone(@PathParam("id") Long userId, TelephoneDTO telephoneRequest) {
        return service.addTelephone(userId, telephoneRequest);
    }

    @PUT
    @Transactional
    @Path("/{userId}/telephones/{telephoneId}")
    public UserResponseDTO updateTelephone(@PathParam("userId") Long userId, @PathParam("telephoneId") Long telephoneId, TelephoneDTO telephoneRequest) {
        return service.updateTelephone(userId, telephoneId, telephoneRequest);
    }

    @POST
    @Transactional
    @Path("/{id}/addresses")
    public UserResponseDTO addAddress(@PathParam("id") Long userId, AddressDTO addressRequest) {
        return service.addAddress(userId, addressRequest);
    }

    @PUT
    @Transactional
    @Path("/{userId}/addresses/{addressId}")
    public UserResponseDTO updateAddress(@PathParam("userId") Long userId, @PathParam("addressId") Long addressId, AddressDTO addressRequest) {
        return service.updateAddress(userId, addressId, addressRequest);
    }
}
