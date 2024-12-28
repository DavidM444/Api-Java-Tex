package ctxt.textil.api.controller;

import ctxt.textil.api.application.dto.response.DatosProvName;
import ctxt.textil.api.application.dto.request.DatosProveedor;
import ctxt.textil.api.application.dto.response.DtoResP;
import ctxt.textil.api.domain.proveedor.*;
import ctxt.textil.api.domain.base.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ApiResponse<DtoResP>> agregarProveedor(@RequestBody @Valid DatosProveedor dtoRgP){
        System.out.println(dtoRgP);
        Proveedor proveedor = proveedorRpty.save(new Proveedor(dtoRgP));
        DtoResP dtoResP = new DtoResP(proveedor.getPrId(), proveedor.getPrNombre(), proveedor.getPrEmpresa(),
                proveedor.getPrTelefono(), proveedor.getPrDireccion());

        if(proveedor.getPrId()==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                false,"Not agregated data",null));

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true,"created proveedor",dtoResP));
    };
    @GetMapping
    public List<DatosProvName> listadoProveedores(){
        List<Proveedor> proveedores = proveedorRpty.findAll();
        return proveedores.stream().map(
                proveedor -> new  DatosProvName(proveedor.getPrNombre(),proveedor.getPrId())
        ).toList();
    }
}
