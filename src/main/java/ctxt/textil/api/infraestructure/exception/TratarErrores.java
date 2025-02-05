package ctxt.textil.api.infraestructure.exception;


import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLIntegrityConstraintViolationException;


@Slf4j
@RestControllerAdvice
public class TratarErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404 (EntityNotFoundException exception){
        log.error("Entidad no encontrada: {}", exception.getMessage());
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

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> constrainError(ConstraintViolationException error){
        log.error("Constrain Violation: {} mesage {}", error.getConstraintViolations(),error.getSuppressed());
        return new ResponseEntity<>("El objeto a insertar no se encuentra identificado", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<String> autorizacionExpired(TokenExpiredException expToken){
        log.error("Token vencido en: {}, Mensaje: {}", expToken.getExpiredOn(), expToken.getMessage());
        return new ResponseEntity<>("Peticion no realizada. Token Expirado", HttpStatusCode.valueOf(403));
    }

    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity tokenInvalidSignature(JWTDecodeException exception){
        log.error("Error asociados al uso de Token JWT:: Mensaje: {}", exception.getMessage());
        return ResponseEntity.badRequest().body("La firma del token es invalida." + exception.getMessage());
    }

    //creacion del dto de error
    public  record DatosErrorDto(String campo, String error){
        //creando constructor, donde se crea campo de la lista de errores que tenemos
        public  DatosErrorDto(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }

    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex) {
        log.error("Error de integridad de datos: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        "DATA_INTEGRITY_ERROR",
                        "Error de integridad de datos",
                        "No se puede procesar la operación debido a restricciones de datos"
                ));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(
                        "ACCESS_DENIED",
                        "Acceso denegado",
                        "No tiene permisos para realizar esta operación"
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        "INVALID_REQUEST",
                        "Solicitud inválida",
                        "El formato de la solicitud es incorrecto"
                ));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        "VALIDATION_ERROR",
                        "Error de validación",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponse> handleOptimisticLocking(OptimisticLockingFailureException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        "CONCURRENT_MODIFICATION",
                        "Error de concurrencia",
                        "El recurso fue modificado por otro usuario"
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        log.error("Error no manejado: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        "INTERNAL_ERROR",
                        "Error interno del servidor",
                        "Ocurrió un error inesperado"
                ));
    }
}
