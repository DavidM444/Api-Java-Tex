package ctxt.textil.api.domain.Dimensiones;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public record DatosDimensiones(double dm_altura, double dm_ancho,Long Registro_re_id) {
}
