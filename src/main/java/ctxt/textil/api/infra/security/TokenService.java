package ctxt.textil.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import ctxt.textil.api.UserAdmin.UserAdmin;
import ctxt.textil.api.Usuario.DataUser;
import ctxt.textil.api.Usuario.Usuario;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService{
    Algorithm algorithm2;
    String Issuer;

    @Value("api.security.secret")
    private String apiSecret;

    @Value("api.security.admin")
    private String apiAdmin;
    public String generarToken(Usuario usuario){
        System.out.println("api secret: "+ apiSecret);
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create().
                    withIssuer("qualityUser").
                    withSubject(usuario.getUsEmail()).
                    withClaim("us_id",usuario.getUsId())
                    .withExpiresAt(generarExpedicionFecha())
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new RuntimeException();
        }
    }

    public String generarAdminToken(UserAdmin userAdmin){
        System.out.println("generadno token admin "+userAdmin.toString());
        Algorithm alg = Algorithm.HMAC256(apiAdmin);
        try {
            return JWT.create().withIssuer("qualityAdmin").withSubject(userAdmin.getAdEmail()).
                    withClaim("ad_id",userAdmin.getAdId())
                    .withExpiresAt(generarExpedicionFecha()).sign(alg);
        }catch (JWTCreationException ex) {
            throw new RuntimeException("Generation Admin Token: error");
        }
    }



    private Instant generarExpedicionFecha() {
        System.out.println("generando fecha");
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
    //subject
    public String getSubject(String token, Boolean uri){
        usoAlgorithm(uri);
        DecodedJWT verifier = null;
        try {

            verifier = JWT.require(algorithm2)
                    .withIssuer(Issuer)
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
    public Integer getIdClaim(String token){
        DecodedJWT verifier = null;
        Algorithm algorithm = Algorithm.HMAC256(apiSecret);
        verifier = JWT.require(algorithm)
                .withIssuer("qualityUser")
                .build()
                .verify(token);

        //change "id" to "us_id"
        return verifier.getClaim("us_id").asInt();

    }

    protected Integer getIdClaimAdmin(String token){
        DecodedJWT verifier = null;
        Algorithm algorithm = Algorithm.HMAC256(apiSecret);
        verifier = JWT.require(algorithm)
                .withIssuer("qualitytex")
                .build()
                .verify(token);

        //change "id" to "us_id"
        return verifier.getClaim("ad_id").asInt();

    }
    protected String getIssClaim(String token, String url){
        Boolean uri = url.equals("/signup/admin");
        usoAlgorithm(uri);
        DecodedJWT verifier = null;
        verifier = JWT.require(algorithm2)
                .withIssuer(Issuer)
                .build()
                .verify(token);
        return verifier.getClaim("iss").asString();


    }
    public void usoAlgorithm(Boolean uri){
        if (uri){
            algorithm2=Algorithm.HMAC256(apiAdmin);
            Issuer = "qualityAdmin";
        } else{
            algorithm2=Algorithm.HMAC256(apiSecret);
            Issuer = "qualityUser";
        }

    }



}
