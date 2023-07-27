package ctxt.textil.api.controller;


import ctxt.textil.api.domain.Proveedor.DtoResP;
import ctxt.textil.api.domain.Proveedor.DtoRgP;
import ctxt.textil.api.domain.Proveedor.Proveedor;
import ctxt.textil.api.domain.Proveedor.ProveedorRpty;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proveedor")
//craeacion de registros en la ruta respectiva y respuesta con dto personalizado
public class ProveedorController {
    @Autowired
    private ProveedorRpty proveedorRpty;

    @PostMapping
    public ResponseEntity<DtoResP> agregarProveedor(@RequestBody @Valid DtoRgP dtoRgP){
        System.out.println(dtoRgP);
        Proveedor proveedor = proveedorRpty.save(new Proveedor(dtoRgP));
        DtoResP dtoResP = new DtoResP(proveedor.getPrId(), proveedor.getPrNombre(), proveedor.getPrEmpresa(),
                proveedor.getPrTelefono(), proveedor.getPrDireccion());
        return ResponseEntity.ok(dtoResP);
    }
}
