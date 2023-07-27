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
@EqualsAndHashCode(of="reId")
public class Registro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reId;
    private String reFecha;
    private Integer proveedorId;

    public Registro(DatosRegistroRegistro datosRegistroRegistro) {
        this.reFecha = datosRegistroRegistro.re_fecha();
        this.proveedorId = datosRegistroRegistro.proveedor_pr_id();
    }
    public void actualizarDatos(@Valid DatosActualizarRegistro datosActualizarRegistro) {
        System.out.println("Entro a actualizar datos---- id a string " + datosActualizarRegistro.id());
        if(datosActualizarRegistro.fecha()!=null){
            this.reFecha = datosActualizarRegistro.fecha();
        }
        if (datosActualizarRegistro.proveedor()!=null && datosActualizarRegistro.proveedor().equals(reId.toString())){
            this.reId = datosActualizarRegistro.id();
        }
    }
}
