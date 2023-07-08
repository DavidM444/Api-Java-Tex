package ctxt.textil.api.RespuestaTodo;

import ctxt.textil.api.Dimensiones.DatosDimensiones;

public record DatosRespuestaTodo(Long reId, String reFecha, Integer proveedorPrId,
                                 DatosDimensiones datosDimensiones) {
}
