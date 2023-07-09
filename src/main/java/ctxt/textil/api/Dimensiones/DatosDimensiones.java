package ctxt.textil.api.Dimensiones;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public record DatosDimensiones(double dm_altura, double dm_ancho, Long registro_re_id) {
}
