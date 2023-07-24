package ctxt.textil.api.domain.Registro;

import jakarta.persistence.*;
import jakarta.validation.Valid;
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
    public void actualizarDatos(@Valid DatosActualizarRegistro datosActualizarRegistro) {
        System.out.println("Entro a actualizar datos---- id a string " + datosActualizarRegistro.id());
        if(datosActualizarRegistro.fecha()!=null){
            this.re_fecha= datosActualizarRegistro.fecha();
        }
        if (datosActualizarRegistro.proveedor()!=null && datosActualizarRegistro.proveedor().equals(re_id.toString())){
            this.re_id = datosActualizarRegistro.id();
        }
    }
}
