package ctxt.textil.api.domain.pabsorcionpilling;

import ctxt.textil.api.application.dto.base.DatosPAbsorcionPilling;
import ctxt.textil.api.domain.base.DerivateClass;
import ctxt.textil.api.domain.base.Updatable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "papilling")
public class PAbsorcionPilling extends DerivateClass implements Updatable<DatosPAbsorcionPilling> {
    private  double paCantidad;
    private double paTiempo;
    private Integer pRango;
    private String pConsideracion;

    public PAbsorcionPilling(DatosPAbsorcionPilling datosPAbsorcionPilling, Long registroId) {
        super(registroId);
        this.paCantidad = datosPAbsorcionPilling.cantidad();
        this.paTiempo = datosPAbsorcionPilling.tiempo();
        this.pRango = datosPAbsorcionPilling.rango();
    }

    //sacar esto al service
    public void actualizarDatos(DatosPAbsorcionPilling datosActPAP) {
        this.pRango = datosActPAP.rango();
        this.paCantidad = datosActPAP.cantidad();
        this.paTiempo = datosActPAP.tiempo();
    }
}