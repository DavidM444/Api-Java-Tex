package ctxt.textil.api.domain.Registro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroRegistro(
        @NotBlank
        String re_fecha,
        Integer proveedor_pr_id,
        @NotNull
        Long userId
) {
}
