package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import med.voll.api.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService{

    @Value("api.security.secret")
    private String apiSecret;
    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create().
                    withIssuer("voll med").
                    withSubject(usuario.getEmail()).
                    withClaim("us_id",usuario.getUs_id())
                    .withExpiresAt(generarExpedicionFecha())
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new RuntimeException();
        }
    }



    private Instant generarExpedicionFecha() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
    //subject
    public String getSubject(String token){

        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("voll med")
                    .build()
                    .verify(token);
            verifier.getSubject();
        }catch (JWTVerificationException exception){
            throw  new RuntimeException(exception.toString());
        }
        if (verifier.getSubject()==null){
            throw  new RuntimeException("verifier es nulo");
        }
        return verifier.getSubject();
    }

}
