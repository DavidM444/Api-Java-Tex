package med.voll.api.PAbsorcionPilling;

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
@Table(name = "pruebaAbsorcion")
public class PAbsorcionPilling {
    private double pa_cantidad;
    private double pa_tiempo;
    private Integer p_rango;
    private String p_consideracion;
    @Id
    private Long registro_re_id;
}