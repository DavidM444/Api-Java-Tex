package ctxt.textil.api.domain.Dimensiones;

import ctxt.textil.api.application.dto.base.DatosDimensiones;
import ctxt.textil.api.domain.DerivateClass;
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
public class Dimensiones extends DerivateClass {
    private double dmAlto;
    private double dmAncho;



    public Dimensiones(DatosDimensiones datosDimensiones, @NotNull Long registroId) {
        super(registroId);
        this.dmAlto = datosDimensiones.altura();
        this.dmAncho = datosDimensiones.ancho();
    }
    public void actualizarDatos(DatosDimensiones datosActDimensiones) {
        if(datosActDimensiones.ancho()==0.0){
            this.dmAlto = datosActDimensiones.altura();
        }
        if (datosActDimensiones.ancho()!=0.0){
            this.dmAncho = datosActDimensiones.ancho();
        }
    }
}
