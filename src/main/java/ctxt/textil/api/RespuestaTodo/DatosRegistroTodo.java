package ctxt.textil.api.RespuestaTodo;

import ctxt.textil.api.domain.ControlPuntos.DatosControlPuntos;
import ctxt.textil.api.domain.EscalaGrises.DatosEscalaGrises;
import ctxt.textil.api.domain.Especificaciones.DatosEspecificaciones;
import ctxt.textil.api.domain.PAbsorcionPilling.DatosPAbsorcionPilling;
import jakarta.validation.constraints.NotBlank;
import ctxt.textil.api.domain.Dimensiones.DatosDimensiones;
//Objeto de datos a recibir del request o frontend
public record DatosRegistroTodo(@NotBlank
                                String fecha,
                                Integer proveedor,
                                DatosDimensiones dimensiones,
                                DatosEspecificaciones especificaciones,
                                DatosEscalaGrises escalagrises,
                                DatosPAbsorcionPilling abpilling,
                                DatosControlPuntos sispuntos){
}
