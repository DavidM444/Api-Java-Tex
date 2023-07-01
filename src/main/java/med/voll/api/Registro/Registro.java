package med.voll.api.Registro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.Dimensiones.Dimensiones;


@Table(name="registros")
@Entity(name="registro")

@Getter
@NoArgsConstructor
@AllArgsConstructor

@EqualsAndHashCode(of="id")
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String fecha;
    private String usuario;
    private String usuarioId;
    private String proveedor;

    @
    private Dimensiones dimensiones;

    public Registro(DatosRegistroRegistro datosRegistroRegistro) {
        this.fecha = datosRegistroRegistro.fecha();
        this.proveedor = datosRegistroRegistro.proveedorId();
        this.usuario = datosRegistroRegistro.usuario();
        this.usuarioId = datosRegistroRegistro.usuarioId();
    }

}
