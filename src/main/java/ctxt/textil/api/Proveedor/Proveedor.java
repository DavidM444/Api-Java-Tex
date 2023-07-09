package ctxt.textil.api.Proveedor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/*
Creacion de proveedores, con constructor
de creacion y actualizacion de datos.
* */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "proveedor")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String empresa;
    private Integer telefono;
    //variables a cambiar para manejo sql

    public Proveedor(DtoRgP dtoRgP) {
        this.nombre = dtoRgP.nombre();
        this.empresa = dtoRgP.empresa();
        this.telefono = dtoRgP.telefono();
    }

    public Proveedor ActualizarDatosProveedor(DatosProveedor datosProveedor){
        this.nombre = datosProveedor.nombre();
        this.empresa = datosProveedor.empresa();
        this.telefono = datosProveedor.telefono();
        return this;
    }

}

