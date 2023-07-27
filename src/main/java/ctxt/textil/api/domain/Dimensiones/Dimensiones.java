package ctxt.textil.api.domain.Dimensiones;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class Dimensiones {
    private double dmAlto;
    private double dmAncho;
    @NotNull
    @Id
    private Long registroId;

    public Dimensiones(DatosDimensiones datosDimensiones) {
        this.dmAlto = datosDimensiones.altura();
        this.dmAncho = datosDimensiones.ancho();
        this.registroId = datosDimensiones.registroId();

    }
    public void actualizarDatos(DatosActDimensiones datosActDimensiones) {
        if(datosActDimensiones.ancho()==0.0){
            this.dmAlto = datosActDimensiones.altura();
        }
        if (datosActDimensiones.ancho()!=0.0){
            this.dmAncho = datosActDimensiones.ancho();
        }
    }
}
