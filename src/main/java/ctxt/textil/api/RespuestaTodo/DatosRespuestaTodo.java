package ctxt.textil.api.RespuestaTodo;

import ctxt.textil.api.Dimensiones.DatosDimensiones;

//Record para la respuesta de del ResponseEntity en las peticiones
public record DatosRespuestaTodo(Long reId, String reFecha, Integer proveedorPrId,
                                 DatosDimensiones datosDimensiones) {
}
