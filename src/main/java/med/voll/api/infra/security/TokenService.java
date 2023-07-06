package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.stereotype.Service;

@Service
public class TokenService{
    public String generarToken(){


        try {
            Algorithm algorithm = Algorithm.HMAC256("2323");
            return JWT.create().withIssuer("auth0").withSubject("voll-med").sign(algorithm);
        }catch (JWTCreationException e){
            throw  new RuntimeException(e);
        }
    }
}
