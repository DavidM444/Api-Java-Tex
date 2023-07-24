package ctxt.textil.api.domain.Proveedor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosProveedor(@NotBlank String nombre, @NotBlank String empresa, Integer telefono, @NotBlank String direccion) {
}
