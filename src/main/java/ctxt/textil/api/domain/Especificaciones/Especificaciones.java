package ctxt.textil.api.domain.Especificaciones;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    private String esRollo;
    private String esPeso;
    private String esTipoTela;
    private String esColor;
    @NotNull
    @Id
    private Long registroId;
    public Especificaciones(DatosEspecificaciones datosEspecificaciones) {
        this.esRollo = datosEspecificaciones.rollo();
        this.esPeso = datosEspecificaciones.peso();
        this.esTipoTela = datosEspecificaciones.tipoTela();
        this.esColor = datosEspecificaciones.color();
        this.registroId = datosEspecificaciones.registroId();
    }

    public void actualizarDatos(DatosActEspecificaciones datosActEspecificaciones) {
        this.esColor = datosActEspecificaciones.color();
        this.esPeso = datosActEspecificaciones.peso();
        this.esTipoTela = datosActEspecificaciones.tipoTela();
        this.esRollo = datosActEspecificaciones.rollo();
    }
}

