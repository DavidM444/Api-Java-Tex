package med.voll.api.Dimensiones;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public record DatosRegistroDimensions(@RequestBody @Valid double altura, double ancho, Integer registro_re_id) {
}
