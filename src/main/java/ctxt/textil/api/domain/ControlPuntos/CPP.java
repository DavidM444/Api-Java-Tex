package ctxt.textil.api.domain.ControlPuntos;
import ctxt.textil.api.application.dto.base.DatosControlPuntos;
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
@Table(name = "controlpuntos")
//Tabla control de calidad 4 puntos con sus respectivos metodos
public class CPP extends DerivateClass {
    private Integer cpPuntuacion;
    private String cpEstado;
    public CPP(DatosControlPuntos datosControlPuntos, Long registroId) {
        super(registroId);
        this.cpPuntuacion = datosControlPuntos.puntuacion();

    }
    public void actualizarDatos(DatosControlPuntos datosActCPP) {
        this.cpPuntuacion = datosActCPP.puntuacion();
    }
}