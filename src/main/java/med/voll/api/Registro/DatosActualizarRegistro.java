package med.voll.api.Registro;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarRegistro(@NotNull Long re_id, String re_fecha, String proveedor_pr_id) {
}
