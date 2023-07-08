package ctxt.textil.api.Proveedor;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Proveedor {
    @Id
    private Long id;
    private String nombre;
    private  String empresa;
    private Integer telefono;

    public Proveedor ActualizarDatosProveedor(DatosProveedor datosProveedor){
        this.nombre = datosProveedor.nombre();
        this.empresa = datosProveedor.empresa();
        this.telefono = datosProveedor.telefono();
        return this;
    }

}

