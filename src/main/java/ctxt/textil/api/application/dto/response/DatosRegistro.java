package ctxt.textil.api.application.dto.response;

import ctxt.textil.api.domain.registro.Registro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistro(
        Long id,
        @NotBlank
        String re_fecha,
        Integer proveedor_pr_id,
        @NotNull
        Long userId
) {
        public DatosRegistro( Registro registro){
                this(registro.getReId(), registro.getReFecha(), registro.getProveedorId(), registro.getUserId());
        }
}
