package ctxt.textil.api.Dimensiones;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "dimensiones")
public class Dimensiones {
    private double dm_altura;
    private double dm_ancho;
    @Id
    private Long registro_re_id;

    public Dimensiones(DatosDimensiones datosDimensiones) {
        this.dm_altura = datosDimensiones.dm_altura();
        this.dm_ancho = datosDimensiones.dm_ancho();
        this.registro_re_id = datosDimensiones.registro_re_id();
    }

    public void actualizarDatos(DatosActDimensiones datosActDimensiones) {
        if(datosActDimensiones.dm_ancho()==0.0){
            this.dm_altura= datosActDimensiones.dm_altura();
        }
        if (datosActDimensiones.dm_ancho()!=0.0){
            this.dm_ancho = datosActDimensiones.dm_ancho();
        }
    }
    //el area se maneja con trigeer

}
