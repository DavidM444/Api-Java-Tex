package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import med.voll.api.usuario.Usuario;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService{
    public String generarToken(Usuario usuario){


        try {
            Algorithm algorithm = Algorithm.HMAC256("2323");
            return JWT.create().
                    withIssuer("voll-med").
                    withSubject(usuario.getEmail()).withClaim("us_id",usuario.getUs_id()).withExpiresAt(generarExpedicionFecha())
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new RuntimeException();
        }
    }

    private Instant generarExpedicionFecha() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
    }
}
