package ctxt.textil.api.domain.ControlPuntos;
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
@Table(name = "controlpuntos")
//Tabla control de calidad 4 puntos con sus respectivos metodos
public class CPP {
    private Integer cp_puntuacion;
    private String cp_estado;
    @Id
    private Long registro_re_id;
    public CPP(DatosControlPuntos datosControlPuntos) {
        this.cp_puntuacion = datosControlPuntos.puntuacion();
        this.registro_re_id = datosControlPuntos.id();
    }
    public void actualizarDatos(DatosActCP datosActCPP) {
        this.cp_puntuacion = datosActCPP.puntuacion();

    }
}