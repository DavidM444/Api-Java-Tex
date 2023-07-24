package ctxt.textil.api.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosAutenticarUsuario(@NotBlank String email, @NotNull String clave) {

}
