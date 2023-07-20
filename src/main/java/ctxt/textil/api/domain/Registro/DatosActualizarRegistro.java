package ctxt.textil.api.domain.Registro;
import ctxt.textil.api.domain.ControlPuntos.DatosActCP;
import ctxt.textil.api.domain.Dimensiones.DatosActDimensiones;
import ctxt.textil.api.domain.EscalaGrises.DatosActEscg;
import ctxt.textil.api.domain.Especificaciones.DatosActEspecificaciones;
import ctxt.textil.api.domain.PAbsorcionPilling.DatosActPAP;
import jakarta.validation.constraints.NotNull;
public record DatosActualizarRegistro(@NotNull Long re_id, String re_fecha, String proveedor_pr_id, DatosActDimensiones datosActDimensiones,
                                      DatosActEspecificaciones datosActEspecificaciones,
                                      DatosActCP datosActCPP, DatosActPAP datosActPAP, DatosActEscg datosActEscg) {
}