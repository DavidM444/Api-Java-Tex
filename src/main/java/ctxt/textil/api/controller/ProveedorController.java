package ctxt.textil.api.controller;


import ctxt.textil.api.Proveedor.DtoResP;
import ctxt.textil.api.Proveedor.DtoRgP;
import ctxt.textil.api.Proveedor.Proveedor;
import ctxt.textil.api.Proveedor.ProveedorRpty;
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
        DtoResP dtoResP = new DtoResP(proveedor.getPr_id(), proveedor.getPr_nombre(), proveedor.getPr_empresa(),
                proveedor.getPr_telefono(), proveedor.getPr_direccion());
        return ResponseEntity.ok(dtoResP);
    }
}
