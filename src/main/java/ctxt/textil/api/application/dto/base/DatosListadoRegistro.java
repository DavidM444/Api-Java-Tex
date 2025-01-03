package ctxt.textil.api.application.dto.base;

import ctxt.textil.api.domain.registro.Registro;

public record DatosListadoRegistro(String fecha, Integer proveedor_pr_id) {
    public DatosListadoRegistro(Registro registro){
        this(registro.getReFecha(), registro.getProveedorId());
    }
}



