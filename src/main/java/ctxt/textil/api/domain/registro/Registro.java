package ctxt.textil.api.domain.registro;

import ctxt.textil.api.application.dto.request.DatosActualizarRegistro;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private Long userId;

    public Registro(@NotBlank  String reFecha, Integer proveedorId,@NotNull Long userId) {
        this.reFecha = reFecha;
        this.proveedorId =proveedorId;
        this.userId = userId;
    }
    public void actualizarDatos(@Valid DatosActualizarRegistro datosActualizarRegistro) {
        System.out.println("Entro a actualizar datos---- id a string " + datosActualizarRegistro.id());
        if(datosActualizarRegistro.fecha()!=null){
            this.reFecha = datosActualizarRegistro.fecha();
        }
        if (datosActualizarRegistro.proveedor()!=null){
            //not update id
            //this.reId = datosActualizarRegistro.id();
            //updating proveedor
            this.proveedorId = datosActualizarRegistro.proveedor();
        }
    }
}
