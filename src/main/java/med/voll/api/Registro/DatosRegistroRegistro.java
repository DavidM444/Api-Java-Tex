package med.voll.api.Registro;

import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotBlank;
import med.voll.api.Dimensiones.DatosDimensiones;
import med.voll.api.Dimensiones.Dimensiones;

public record DatosRegistroRegistro(
        @NotBlank
        String re_fecha,
        Integer proveedor_pr_id

) {
}
