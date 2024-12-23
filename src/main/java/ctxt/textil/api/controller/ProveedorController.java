package ctxt.textil.api.controller;

import ctxt.textil.api.domain.PAbsorcionPilling.DatosProvName;
import ctxt.textil.api.domain.Proveedor.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *  Creaciòn de registros en la ruta respectiva y respuesta con dto personalizado
 * */

@RestController
@RequestMapping("/proveedor")
public class ProveedorController {
    @Autowired
    private ProveedorRpty proveedorRpty;

    @PostMapping
    public ResponseEntity<DtoResP> agregarProveedor(@RequestBody @Valid DatosProveedor dtoRgP){
        System.out.println(dtoRgP);
        Proveedor proveedor = proveedorRpty.save(new Proveedor(dtoRgP));
        DtoResP dtoResP = new DtoResP(proveedor.getPrId(), proveedor.getPrNombre(), proveedor.getPrEmpresa(),
                proveedor.getPrTelefono(), proveedor.getPrDireccion());
        return ResponseEntity.ok(dtoResP);
    };
    @GetMapping
    public List<DatosProvName> listadoProveedores(){
        List<Proveedor> proveedores = proveedorRpty.findAll();
        return proveedores.stream().map(
                proveedor -> new  DatosProvName(proveedor.getPrNombre(),proveedor.getPrId())
        ).toList();
    }
}
