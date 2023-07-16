package ctxt.textil.api.PAbsorcionPilling;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//Tabla Pruebas de absorcion y Pilling con sus respectivos metodos
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "papilling")
public class PAbsorcionPilling {
    private  double pa_cantidad;
    private double pa_tiempo;
    private Integer p_rango;
    private String p_consideracion;
    @Id
    private Long registro_re_id;
    public PAbsorcionPilling(DatosPAbsorcionPilling datosPAbsorcionPilling) {
        this.pa_cantidad = datosPAbsorcionPilling.pa_cantidad();
        this.pa_tiempo = datosPAbsorcionPilling.pa_tiempo();
        this.p_rango = datosPAbsorcionPilling.p_rango();
        this.registro_re_id = datosPAbsorcionPilling.id();
    }
    public void actualizarDatos(DatosActPAP datosActPAP) {
        this.p_rango = datosActPAP.p_rango();
        this.pa_cantidad = datosActPAP.pa_cantidad();
        this.pa_tiempo = datosActPAP.pa_tiempo();
    }
}
