package ctxt.textil.api.ControlPuntos;
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
@Table(name = "controlPuntos")
public class CPP {
    private Integer cp_puntuacion;
    private String cp_estado;
    @Id
    private Long registro_re_id;

    public CPP(DatosControlPuntos datosControlPuntos) {
        this.cp_puntuacion = datosControlPuntos.cp_puntuacion();
    }

    public void actualizarDatos(DatosActCP datosActCPP) {
        this.cp_puntuacion = datosActCPP.cp_puntuacion();

    }
}