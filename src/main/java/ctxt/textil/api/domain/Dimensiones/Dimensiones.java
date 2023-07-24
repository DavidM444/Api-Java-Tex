package ctxt.textil.api.domain.Dimensiones;

import jakarta.persistence.*;
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
    private double dm_alto;
    private double dm_ancho;
    @Id
    private Long registro_re_id;

    public Dimensiones(DatosDimensiones datosDimensiones) {
        this.dm_alto = datosDimensiones.altura();
        this.dm_ancho = datosDimensiones.ancho();
        this.registro_re_id = datosDimensiones.registroId();

    }
    public void actualizarDatos(DatosActDimensiones datosActDimensiones) {
        if(datosActDimensiones.ancho()==0.0){
            this.dm_alto = datosActDimensiones.altura();
        }
        if (datosActDimensiones.ancho()!=0.0){
            this.dm_ancho = datosActDimensiones.ancho();
        }
    }
}
