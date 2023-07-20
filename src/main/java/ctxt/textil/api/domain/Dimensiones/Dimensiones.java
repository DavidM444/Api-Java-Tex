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
        this.dm_alto = datosDimensiones.dm_altura();
        this.dm_ancho = datosDimensiones.dm_ancho();
        this.registro_re_id = datosDimensiones.Registro_re_id();

    }
    public void actualizarDatos(DatosActDimensiones datosActDimensiones) {
        if(datosActDimensiones.dm_ancho()==0.0){
            this.dm_alto = datosActDimensiones.dm_altura();
        }
        if (datosActDimensiones.dm_ancho()!=0.0){
            this.dm_ancho = datosActDimensiones.dm_ancho();
        }
    }
    //el area se maneja con trigeer
}
