package ctxt.textil.api.domain.Proveedor;

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
    private Long prId;
    private String prNombre;
    private String prEmpresa;
    private String prDireccion;
    private Integer prTelefono;
    //variables a cambiar para manejo sql
    public Proveedor(DatosProveedor dtoRgP) {
        this.prNombre = dtoRgP.nombre();
        this.prEmpresa = dtoRgP.empresa();
        this.prTelefono = dtoRgP.telefono();
        this.prDireccion = dtoRgP.direccion();
    }

    //only method for admins, they may edit the data proveedor
    public Proveedor ActualizarDatosProveedor(DatosProveedor datosProveedor){
        this.prNombre = datosProveedor.nombre();
        this.prEmpresa = datosProveedor.empresa();
        this.prTelefono = datosProveedor.telefono();
        this.prDireccion = datosProveedor.direccion();
        return this;
    }
}

