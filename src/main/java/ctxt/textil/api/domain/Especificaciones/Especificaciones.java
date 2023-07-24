package ctxt.textil.api.domain.Especificaciones;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//Tabla especificaciones con sus respectivos metodos
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "especificacion")
public class Especificaciones {
    private String es_rollo;
    private String es_peso;
    private String es_tipoTela;
    private String es_color;
    @Id
    private Long registro_re_id;
    public Especificaciones(DatosEspecificaciones datosEspecificaciones) {
        this.es_rollo = datosEspecificaciones.rollo();
        this.es_peso = datosEspecificaciones.peso();
        this.es_tipoTela = datosEspecificaciones.tipoTela();
        this.es_color = datosEspecificaciones.color();
        this.registro_re_id = datosEspecificaciones.id();
    }

    public void actualizarDatos(DatosActEspecificaciones datosActEspecificaciones) {
        this.es_color = datosActEspecificaciones.color();
        this.es_peso = datosActEspecificaciones.peso();
        this.es_tipoTela = datosActEspecificaciones.tipoTela();
        this.es_rollo = datosActEspecificaciones.rollo();
    }
}

