package ctxt.textil.api.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosProveedor(@NotBlank String nombre, @NotBlank String empresa, Integer telefono, @NotBlank String direccion) {
}
