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
    private Long pr_id;
    private String pr_nombre;
    private String pr_empresa;
    private String pr_direccion;
    private Integer pr_telefono;
    //variables a cambiar para manejo sql
    public Proveedor(DtoRgP dtoRgP) {
        this.pr_nombre = dtoRgP.nombre();
        this.pr_empresa = dtoRgP.empresa();
        this.pr_telefono = dtoRgP.telefono();
        this.pr_direccion = dtoRgP.direccion();
    }
    public Proveedor ActualizarDatosProveedor(DatosProveedor datosProveedor){
        this.pr_nombre = datosProveedor.nombre();
        this.pr_empresa = datosProveedor.empresa();
        this.pr_telefono = datosProveedor.telefono();
        this.pr_direccion = datosProveedor.direccion();
        return this;
    }
}

