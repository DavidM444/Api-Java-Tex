package med.voll.api.Registro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name="registros")
@Entity(name="registro")

@Getter
@NoArgsConstructor
@AllArgsConstructor

@EqualsAndHashCode(of="id")
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long re_id;
    private String re_fecha;
    private Integer proveedor_pr_id;




    public Registro(DatosRegistroRegistro datosRegistroRegistro) {
        this.re_fecha = datosRegistroRegistro.re_fecha();
        this.proveedor_pr_id = datosRegistroRegistro.proveedor_pr_id();

    }

}
