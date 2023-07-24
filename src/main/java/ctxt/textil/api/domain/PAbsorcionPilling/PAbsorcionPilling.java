package ctxt.textil.api.domain.PAbsorcionPilling;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        this.pa_cantidad = datosPAbsorcionPilling.cantidad();
        this.pa_tiempo = datosPAbsorcionPilling.tiempo();
        this.p_rango = datosPAbsorcionPilling.rango();
        this.registro_re_id = datosPAbsorcionPilling.id();
    }
    public void actualizarDatos(DatosActPAP datosActPAP) {
        this.p_rango = datosActPAP.p_rango();
        this.pa_cantidad = datosActPAP.pa_cantidad();
        this.pa_tiempo = datosActPAP.pa_tiempo();
    }
}