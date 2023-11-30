package ctxt.textil.api.domain.EscalaGrises;

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
@Table(name = "escalagrises")
public class EscalaGrises {
    private Integer esgCalificacion;
    private String esgValoracion;
    @NotNull
    @Id
    private Long registroId;
    public EscalaGrises(DatosEscalaGrises datosEscalaGrises) {
        this.esgCalificacion = datosEscalaGrises.valoracion();
        this.registroId = datosEscalaGrises.id();

    }
    public void actualizarDatos(DatosActEscg datosActEscg) {
        this.esgCalificacion = datosActEscg.calificacion();
    }
}