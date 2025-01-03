package ctxt.textil.api.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DatosNewUser(String nombre, String apellido,@NotBlank String email, @NotBlank String clave) {
}
