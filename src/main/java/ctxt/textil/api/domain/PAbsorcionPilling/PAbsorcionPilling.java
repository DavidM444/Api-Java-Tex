package ctxt.textil.api.domain.PAbsorcionPilling;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "papilling")
public class PAbsorcionPilling {
    private  double paCantidad;
    private double paTiempo;
    private Integer pRango;
    private String pConsideracion;
    @NotNull
    @Id
    private Long registroId;
    public PAbsorcionPilling(DatosPAbsorcionPilling datosPAbsorcionPilling) {
        this.paCantidad = datosPAbsorcionPilling.cantidad();
        this.paTiempo = datosPAbsorcionPilling.tiempo();
        this.pRango = datosPAbsorcionPilling.rango();
        this.registroId = datosPAbsorcionPilling.id();
    }
    public void actualizarDatos(DatosActPAP datosActPAP) {
        this.pRango = datosActPAP.p_rango();
        this.paCantidad = datosActPAP.pa_cantidad();
        this.paTiempo = datosActPAP.pa_tiempo();
    }
}