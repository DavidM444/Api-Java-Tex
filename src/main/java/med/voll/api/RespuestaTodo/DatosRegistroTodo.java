package med.voll.api.RespuestaTodo;

import jakarta.validation.constraints.NotBlank;
import med.voll.api.Dimensiones.DatosDimensiones;

public record DatosRegistroTodo(@NotBlank
                                String re_fecha,
                                Integer proveedor_pr_id,
                                DatosDimensiones dimensiones){
}
