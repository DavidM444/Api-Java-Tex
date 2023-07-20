package ctxt.textil.api.RespuestaTodo;

import ctxt.textil.api.domain.ControlPuntos.DatosControlPuntos;
import ctxt.textil.api.domain.Dimensiones.DatosDimensiones;
import ctxt.textil.api.domain.EscalaGrises.DatosEscalaGrises;
import ctxt.textil.api.domain.Especificaciones.DatosEspecificaciones;
import ctxt.textil.api.domain.PAbsorcionPilling.DatosPAbsorcionPilling;

//Record para la respuesta de del ResponseEntity en las peticiones
public record DatosRespuestaTodo(Long reId, String reFecha, Integer proveedorPrId,
                                 DatosDimensiones datosDimensiones,
                                 DatosEspecificaciones especificaciones,
                                 DatosEscalaGrises escalagrises,
                                 DatosPAbsorcionPilling abpilling,
                                 DatosControlPuntos sispuntos) {
}
