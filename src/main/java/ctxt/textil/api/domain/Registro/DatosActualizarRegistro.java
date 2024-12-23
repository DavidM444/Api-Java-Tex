package ctxt.textil.api.domain.Registro;
import ctxt.textil.api.application.dto.base.DatosDimensiones;
import ctxt.textil.api.application.dto.base.DatosEscalaGrises;

import ctxt.textil.api.application.dto.base.DatosEspecificaciones;
import ctxt.textil.api.application.dto.base.DatosPAbsorcionPilling;
import ctxt.textil.api.application.dto.base.DatosControlPuntos;
import jakarta.validation.constraints.NotNull;
public record DatosActualizarRegistro(@NotNull Long id, String fecha, Integer proveedor, DatosDimensiones dimensiones,
                                      DatosEspecificaciones especificaciones,
                                      DatosControlPuntos sispuntos, DatosPAbsorcionPilling abpilling, DatosEscalaGrises escalagrises) {
}