package ctxt.textil.api.application.dto.base;

import ctxt.textil.api.domain.Dimensiones.Dimensiones;
import ctxt.textil.api.domain.Registro.Registro;

public record DatosDimensiones(double altura, double ancho) {
    public DatosDimensiones(Dimensiones dimensiones){
        this(dimensiones.getDmAlto(), dimensiones.getDmAncho());
    }
}