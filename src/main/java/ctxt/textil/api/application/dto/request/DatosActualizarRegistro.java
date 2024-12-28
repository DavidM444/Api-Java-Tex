package ctxt.textil.api.application.dto.request;
import ctxt.textil.api.application.dto.base.*;
import jakarta.validation.constraints.NotNull;
public record DatosActualizarRegistro(@NotNull Long id, String fecha, Integer proveedor, DatosDimensiones dimensiones,
                                      DatosEspecificaciones especificaciones,
                                      DatosControlPuntos sispuntos, DatosPAbsorcionPilling abpilling, DatosEscalaGrises escalagrises) {
}