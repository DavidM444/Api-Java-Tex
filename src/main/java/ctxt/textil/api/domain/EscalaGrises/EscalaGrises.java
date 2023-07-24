package ctxt.textil.api.domain.EscalaGrises;

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
@Table(name = "escalagrises")
public class EscalaGrises {
    private Integer esg_calificacion;
    private String esg_valoracion;
    @Id
    private Long registro_re_id;
    public EscalaGrises(DatosEscalaGrises datosEscalaGrises) {
        this.esg_calificacion = datosEscalaGrises.valoracion();
        this.registro_re_id = datosEscalaGrises.id();

    }
    public void actualizarDatos(DatosActEscg datosActEscg) {
        this.esg_calificacion = datosActEscg.esg_calificacion();
    }
}