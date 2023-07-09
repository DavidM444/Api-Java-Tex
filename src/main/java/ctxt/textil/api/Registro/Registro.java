package ctxt.textil.api.Registro;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
//tabla registro con valiadcion al actualizar datos
@Table(name="registros")
@Entity(name="registro")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="re_id")
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
        System.out.println("Entro a actualizar datos---- id a string " + datosActualizarRegistro.re_id());
        if(datosActualizarRegistro.re_fecha()!=null){
            this.re_fecha= datosActualizarRegistro.re_fecha();
        }
        if (datosActualizarRegistro.proveedor_pr_id()!=null && datosActualizarRegistro.proveedor_pr_id().equals(re_id.toString())){
            this.re_id = datosActualizarRegistro.re_id();
        }
    }
}
