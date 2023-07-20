package ctxt.textil.api.Registro;
import ctxt.textil.api.ControlPuntos.DatosActCP;
import ctxt.textil.api.Dimensiones.DatosActDimensiones;
import ctxt.textil.api.EscalaGrises.DatosActEscg;
import ctxt.textil.api.Especificaciones.DatosActEspecificaciones;
import ctxt.textil.api.PAbsorcionPilling.DatosActPAP;
import jakarta.validation.constraints.NotNull;
public record DatosActualizarRegistro(@NotNull Long re_id, String re_fecha, String proveedor_pr_id, DatosActDimensiones datosActDimensiones,
                                      DatosActEspecificaciones datosActEspecificaciones,
                                      DatosActCP datosActCPP, DatosActPAP datosActPAP, DatosActEscg datosActEscg) {
}