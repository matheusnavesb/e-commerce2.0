package org.acme.Resource;

import org.acme.dto.AuthDTO;
import org.acme.dto.UserResponseDTO;
import org.acme.service.HashService;
import org.acme.service.JwtService;
import org.acme.service.UserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/auth-user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    HashService hashService;

    @Inject
    UserService userService;

    @Inject
    JwtService jwtService;

    @POST
    public Response login(AuthDTO authDTO) {
        
        String hash = hashService.getHashSenha(authDTO.password());

        UserResponseDTO usuario = userService.findByEmailAndPassword(authDTO.login(), hash);

        if (usuario != null) {
            return Response.ok(usuario)
                    .header("Authorization", jwtService.generateJwt(usuario))
                    .build();
        } else {
            return Response.status(Status.NOT_FOUND)
                    .entity("Username ou senha inválido")
                    .build();
        }

    }
}
