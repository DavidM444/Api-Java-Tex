package ctxt.textil.api.RespuestaTodo;

import ctxt.textil.api.ControlPuntos.DatosControlPuntos;
import ctxt.textil.api.Dimensiones.DatosDimensiones;
import ctxt.textil.api.EscalaGrises.DatosEscalaGrises;
import ctxt.textil.api.Especificaciones.DatosEspecificaciones;
import ctxt.textil.api.PAbsorcionPilling.DatosPAbsorcionPilling;

//Record para la respuesta de del ResponseEntity en las peticiones
public record DatosRespuestaTodo(Long reId, String reFecha, Integer proveedorPrId,
                                 DatosDimensiones datosDimensiones,
                                 DatosEspecificaciones especificaciones,
                                 DatosEscalaGrises escalagrises,
                                 DatosPAbsorcionPilling abpilling,
                                 DatosControlPuntos sispuntos) {
}
