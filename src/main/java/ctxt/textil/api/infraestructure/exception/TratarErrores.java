package ctxt.textil.api.infraestructure.exception;


import com.auth0.jwt.exceptions.JWTDecodeException;

import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLIntegrityConstraintViolationException;


@RestControllerAdvice
public class TratarErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404 (){
        System.out.println("error 404-------sdf");
        return ResponseEntity.notFound().build();

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    //para dar respuestas de los errores recibimos como parametro la excepcion
    public ResponseEntity tratarError400 (MethodArgumentNotValidException e){
        var errores = e.getFieldErrors().stream().map(DatosErrorDto::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }
    @ExceptionHandler({SQLIntegrityConstraintViolationException.class,NoSuchAlgorithmException.class})
    public ResponseEntity tratarError500 (){
        return ResponseEntity.internalServerError().build();

    }


    //creacion del dto de error
    public  record DatosErrorDto(String campo, String error){
        //creando constructor, donde se crea campo de la lista de errores que tenemos
        public  DatosErrorDto(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }

    }

    //constraint key violation
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> constrainError(ConstraintViolationException error){
        System.out.println("error "+ error.getConstraintViolations() +" mesage " +error.getSuppressed());

        return new ResponseEntity<>("El objeto a insertar no se encuentra identificado", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<String> autorizacionExpired(){
        System.out.println("error de token vencido ");
        return new ResponseEntity<>("Peticion no realizada. Token Expirado", HttpStatusCode.valueOf(403));
    }

    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<String> tokenInvalidSignature(JWTDecodeException exception){
        System.out.println("retornando error personalizado de invalid signature token");
        return ResponseEntity.badRequest().body("La firma del token es invalida." + exception.getMessage());
    }


}