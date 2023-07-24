package ctxt.textil.api.domain.Registro;
import ctxt.textil.api.domain.ControlPuntos.DatosActCP;
import ctxt.textil.api.domain.Dimensiones.DatosActDimensiones;
import ctxt.textil.api.domain.EscalaGrises.DatosActEscg;
import ctxt.textil.api.domain.Especificaciones.DatosActEspecificaciones;
import ctxt.textil.api.domain.PAbsorcionPilling.DatosActPAP;
import jakarta.validation.constraints.NotNull;
public record DatosActualizarRegistro(@NotNull Long id, String fecha, String proveedor, DatosActDimensiones dimensiones,
                                      DatosActEspecificaciones especificaciones,
                                      DatosActCP sispuntos, DatosActPAP abpilling, DatosActEscg escalagrises) {
}