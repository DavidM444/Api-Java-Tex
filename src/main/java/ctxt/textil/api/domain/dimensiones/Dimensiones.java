package ctxt.textil.api.domain.dimensiones;

import ctxt.textil.api.application.dto.base.DatosDimensiones;
import ctxt.textil.api.domain.base.DerivateClass;
import ctxt.textil.api.domain.base.Updatable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//Tabla dimensiones con sus respectivos metodos

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dimensiones")
public class Dimensiones extends DerivateClass implements Updatable<DatosDimensiones> {
    private double dmAlto;
    private double dmAncho;



    public Dimensiones(DatosDimensiones datosDimensiones, @NotNull Long registroId) {
        super(registroId);
        this.dmAlto = datosDimensiones.altura();
        this.dmAncho = datosDimensiones.ancho();
    }
    @Override
    public void actualizarDatos(DatosDimensiones datos) {
        if (datos == null) {
            throw new IllegalArgumentException("Los datos de dimensiones no pueden ser nulos");
        }

        // Validar y actualizar altura
        if (datos.altura() > 0) {
            this.dmAlto = datos.altura();
        }

        // Validar y actualizar ancho
        if (datos.ancho() > 0) {
            this.dmAncho = datos.ancho();
        }
    }
}
