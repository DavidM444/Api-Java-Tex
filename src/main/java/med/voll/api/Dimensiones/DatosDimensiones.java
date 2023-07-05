package med.voll.api.Dimensiones;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public record DatosRegistroDimensiones(@RequestBody @Valid double altura, double ancho, Long registro_re_id) {
}
