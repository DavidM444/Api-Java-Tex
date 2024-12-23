package ctxt.textil.api.domain.EscalaGrises;

import ctxt.textil.api.application.dto.base.DatosEscalaGrises;
import ctxt.textil.api.domain.DerivateClass;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "escalagrises")
public class EscalaGrises extends DerivateClass {
    private Integer esgCalificacion;
    private String esgValoracion;

    public EscalaGrises(DatosEscalaGrises datosEscalaGrises, Long registroId) {
        super(registroId);
        this.esgCalificacion = datosEscalaGrises.calificacion();

    }
    public void actualizarDatos(DatosEscalaGrises datosActEscg) {
        this.esgCalificacion = datosActEscg.calificacion();
    }
}