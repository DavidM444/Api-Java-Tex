package ctxt.textil.api.RespuestaTodo;

import ctxt.textil.api.ControlPuntos.DatosControlPuntos;
import ctxt.textil.api.EscalaGrises.DatosEscalaGrises;
import ctxt.textil.api.Especificaciones.DatosEspecificaciones;
import ctxt.textil.api.PAbsorcionPilling.DatosPAbsorcionPilling;
import jakarta.validation.constraints.NotBlank;
import ctxt.textil.api.Dimensiones.DatosDimensiones;

public record DatosRegistroTodo(@NotBlank
                                String re_fecha,
                                Integer proveedor_pr_id,
                                DatosDimensiones dimensiones,
                                DatosEspecificaciones datosEspecificaciones,
                                DatosEscalaGrises datosEscalaGrises,
                                DatosPAbsorcionPilling datosPAbsorcionPilling,
                                DatosControlPuntos datosControlPuntos){
}