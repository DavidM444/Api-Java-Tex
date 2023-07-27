package ctxt.textil.api.domain.ControlPuntos;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "controlpuntos")
//Tabla control de calidad 4 puntos con sus respectivos metodos
public class CPP {
    private Integer cpPuntuacion;
    private String cpEstado;
    @NotNull
    @Id
    private Long registroId;
    public CPP(DatosControlPuntos datosControlPuntos) {
        this.cpPuntuacion = datosControlPuntos.puntuacion();
        this.registroId = datosControlPuntos.id();
    }
    public void actualizarDatos(DatosActCP datosActCPP) {
        this.cpPuntuacion = datosActCPP.puntuacion();

    }
}