package ctxt.textil.api.Especificaciones;

import jakarta.persistence.*;

@Table(name = "especificacion")
public class Especificaciones {
    private String es_rollo;
    private String es_peso;
    private String es_tipoTela;
    private Long registro_re_id;
}

