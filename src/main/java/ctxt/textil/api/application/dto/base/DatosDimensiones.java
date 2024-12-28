package ctxt.textil.api.application.dto.base;

import ctxt.textil.api.domain.dimensiones.Dimensiones;

public record DatosDimensiones(double altura, double ancho){
    public DatosDimensiones(Dimensiones dimensiones){
        this(dimensiones.getDmAlto(), dimensiones.getDmAncho());
    }


}