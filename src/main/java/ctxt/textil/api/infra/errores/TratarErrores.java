package ctxt.textil.api.infra.errores;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLIntegrityConstraintViolationException;

//clase para tratar errores a nivel de entidades y no a  nivel de medicos
@RestControllerAdvice
public class TratarErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404 (){
        return ResponseEntity.notFound().build();

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    //para dar respuestas de los errores recibimos como parametro  la excepcion
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


}