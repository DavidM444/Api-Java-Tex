package ctxt.textil.api.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosAutenticarUsuario(@NotBlank String email, @NotNull String clave) {

}
