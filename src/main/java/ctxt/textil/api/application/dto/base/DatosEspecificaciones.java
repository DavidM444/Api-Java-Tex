package ctxt.textil.api.application.dto.base;

import ctxt.textil.api.domain.especificaciones.Especificaciones;

public record DatosEspecificaciones(String rollo, String peso, String tipoTela,
                                    String color) {
    public DatosEspecificaciones(Especificaciones esp) {
        this(esp.getEsRollo(),esp.getEsPeso(),esp.getEsTipoTela(),esp.getEsColor()
        ) ;
    }
}
