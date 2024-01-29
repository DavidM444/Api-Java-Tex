package ctxt.textil.api.RespuestaTodo;

import ctxt.textil.api.domain.ControlPuntos.DatosActCP;
import ctxt.textil.api.domain.Dimensiones.DatosActDimensiones;
import ctxt.textil.api.domain.EscalaGrises.DatosList;
import ctxt.textil.api.domain.Especificaciones.DatosActEspecificaciones;
import ctxt.textil.api.domain.PAbsorcionPilling.DatosActPAP;

//Record para la respuesta de del ResponseEntity en las peticiones
public record DatosRespuestaTodo(Long id, String fecha, String proveedor, String empresa,
                                 DatosActDimensiones datosDimensiones,
                                 DatosActEspecificaciones especificaciones,
                                 DatosList escalagrises,
                                 DatosActPAP abpilling,
                                 DatosActCP sispuntos) {
}
