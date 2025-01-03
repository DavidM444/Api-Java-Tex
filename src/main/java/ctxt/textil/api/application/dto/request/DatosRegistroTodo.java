package ctxt.textil.api.application.dto.request;

import ctxt.textil.api.application.dto.base.DatosControlPuntos;
import ctxt.textil.api.application.dto.base.DatosEscalaGrises;
import ctxt.textil.api.application.dto.base.DatosEspecificaciones;
import ctxt.textil.api.application.dto.base.DatosPAbsorcionPilling;
import jakarta.validation.constraints.NotBlank;
import ctxt.textil.api.application.dto.base.DatosDimensiones;
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
