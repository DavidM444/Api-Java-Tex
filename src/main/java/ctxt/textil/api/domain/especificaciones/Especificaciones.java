package ctxt.textil.api.domain.especificaciones;

import ctxt.textil.api.application.dto.base.DatosEspecificaciones;
import ctxt.textil.api.domain.base.DerivateClass;
import ctxt.textil.api.domain.base.Updatable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//Tabla especificaciones con sus respectivos metodos
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "especificacion")
public class Especificaciones extends DerivateClass implements Updatable<DatosEspecificaciones> {
    @NotBlank
    private String esRollo;
    private String esPeso;
    private String esTipoTela;
    private String esColor;

    public Especificaciones(DatosEspecificaciones datosEspecificaciones, Long registroId) {
        super(registroId);
        this.esRollo = datosEspecificaciones.rollo();
        this.esPeso = datosEspecificaciones.peso();
        this.esTipoTela = datosEspecificaciones.tipoTela();
        this.esColor = datosEspecificaciones.color();

    }

    public void actualizarDatos(DatosEspecificaciones datosActEspecificaciones) {
        this.esColor = datosActEspecificaciones.color();
        this.esPeso = datosActEspecificaciones.peso();
        this.esTipoTela = datosActEspecificaciones.tipoTela();
        this.esRollo = datosActEspecificaciones.rollo();
    }
}

